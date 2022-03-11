package cc.ewell.dubbo.admin.dubbo.impl;

import cc.ewell.dubbo.admin.common.exception.VersionValidationException;
import cc.ewell.dubbo.admin.common.util.*;
import cc.ewell.dubbo.admin.dto.req.ApiRegistryReq;
import cc.ewell.dubbo.admin.dto.req.ApiServiceTestReq;
import cc.ewell.dubbo.admin.dubbo.service.ApiServiceDubboService;
import cc.ewell.dubbo.admin.model.domain.MethodMetadata;
import cc.ewell.dubbo.admin.model.domain.Provider;
import cc.ewell.dubbo.admin.service.ApiRegistryService;
import cc.ewell.dubbo.admin.service.ConsumerService;
import cc.ewell.dubbo.admin.service.ProviderService;
import cc.ewell.dubbo.admin.service.impl.GenericServiceImpl;
import co.faao.plugin.transmission.EmrCodeUtils;
import co.faao.plugin.transmission.request.MessageHeader;
import co.faao.plugin.transmission.response.MessageResult;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.google.gson.JsonParseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.metadata.definition.model.FullServiceDefinition;
import org.apache.dubbo.metadata.definition.model.MethodDefinition;
import org.apache.dubbo.metadata.report.identifier.MetadataIdentifier;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xushiling
 * @description 服务管理
 * @createDate 2021/11/22 11:18 上午
 */
@Api(value = "服务管理", tags = "服务管理")
@DubboService(version = "2.0.0")
public class ApiServiceDubboServiceImpl implements ApiServiceDubboService {

    @Resource
    ApiRegistryService apiRegistryService;
    @Resource
    ProviderService providerService;
    @Resource
    ConsumerService consumerService;
    @Resource
    GenericServiceImpl genericService;

    Gson gson = new Gson();

    @ApiOperation(value = "查询所有服务", notes = "查询所有服务")
    @Override
    public MessageResult allServices(MessageHeader header) {
        try {
            ApiRegistryReq apiRegistryReq = apiRegistryService.getRegistryCenter("1");
            providerService.init(apiRegistryReq.getRegistryUrl());
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002", providerService.findServices());
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }

    @ApiOperation(value = "查询所有应用", notes = "查询所有应用")
    @Override
    public MessageResult allApplications(MessageHeader header) {
        try {
            ApiRegistryReq apiRegistryReq = apiRegistryService.getRegistryCenter("1");
            providerService.init(apiRegistryReq.getRegistryUrl());
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002", providerService.findApplications());
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }

    @ApiOperation(value = "服务详情（方法列表）", notes = "服务详情")
    @Override
    public MessageResult methodList(MessageHeader header) {
        try {
            String service = header.parsekey("service");
            ApiRegistryReq apiRegistryReq = apiRegistryService.getRegistryCenter("1");
            providerService.init(apiRegistryReq.getRegistryUrl());
            consumerService.init(apiRegistryReq.getRegistryUrl());

            service = service.replace(Constants.ANY_VALUE, Constants.PATH_SEPARATOR);
            String group = Tool.getGroup(service);
            String version = Tool.getVersion(service);
            String interfaze = Tool.getInterface(service);
            List<Provider> providers = providerService.findByService(service);
            String application = null;
            if (providers != null && providers.size() > 0) {
                application = providers.get(0).getApplication();
            }
            MetadataIdentifier identifier = new MetadataIdentifier(interfaze, version, group, Constants.PROVIDER_SIDE, application);
            String metadata = providerService.getProviderMetaData(identifier);
            FullServiceDefinition serviceDefinition = new FullServiceDefinition();
            if (metadata != null) {
                try {
                    serviceDefinition = gson.fromJson(metadata, FullServiceDefinition.class);
                } catch (JsonParseException e) {
                    throw new VersionValidationException("dubbo 2.6 does not support metadata");
                }
            }
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002", serviceDefinition.getMethods());
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }


    @ApiOperation(value = "方法参数信息", notes = "服务详情")
    @Override
    public MessageResult methodParams(String application, String service, String method) {
        try {
            ApiRegistryReq apiRegistryReq = apiRegistryService.getRegistryCenter("1");
            providerService.init(apiRegistryReq.getRegistryUrl());
            Map<String, String> info = ConvertUtil.serviceName2Map(service);
            MetadataIdentifier identifier = new MetadataIdentifier(info.get(Constants.INTERFACE_KEY),
                    info.get(Constants.VERSION_KEY),
                    info.get(Constants.GROUP_KEY), Constants.PROVIDER_SIDE, application);
            String metadata = providerService.getProviderMetaData(identifier);
            MethodMetadata methodMetadata = null;
            if (metadata != null) {
                com.google.gson.Gson gson = new com.google.gson.Gson();
                String release = providerService.findVersionInApplication(application);
                if (release.startsWith("2.")) {
                    cc.ewell.dubbo.admin.model.domain.FullServiceDefinition serviceDefinition = gson.fromJson(metadata,
                            cc.ewell.dubbo.admin.model.domain.FullServiceDefinition.class);
                    List<cc.ewell.dubbo.admin.model.domain.MethodDefinition> methods = serviceDefinition.getMethods();
                    if (methods != null) {
                        for (cc.ewell.dubbo.admin.model.domain.MethodDefinition m : methods) {
                            if (ServiceTestUtil.sameMethod(m, method)) {
                                methodMetadata = ServiceTestUtil.generateMethodMeta(serviceDefinition, m);
                                break;
                            }
                        }
                    }
                } else {
                    FullServiceDefinition serviceDefinition = gson.fromJson(metadata,
                            FullServiceDefinition.class);
                    List<MethodDefinition> methods = serviceDefinition.getMethods();
                    if (methods != null) {
                        for (MethodDefinition m : methods) {
                            if (ServiceTestV3Util.sameMethod(m, method)) {
                                methodMetadata = ServiceTestV3Util.generateMethodMeta(serviceDefinition, m);
                                break;
                            }
                        }
                    }
                }
            }
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002", methodMetadata);
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }


    @ApiOperation(value = "服务测试", notes = "服务测试")
    @Override
    public MessageResult testMethod(ApiServiceTestReq serviceTestDTO) {
        try {
            Object result = genericService.invoke(serviceTestDTO.getService(), serviceTestDTO.getMethod(), serviceTestDTO.getParameterTypes(), serviceTestDTO.getParams());
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002", result);
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }

}
