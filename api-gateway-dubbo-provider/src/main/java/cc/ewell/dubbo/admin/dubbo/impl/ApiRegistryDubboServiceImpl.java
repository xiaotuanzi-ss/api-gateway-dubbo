package cc.ewell.dubbo.admin.dubbo.impl;

import cc.ewell.dubbo.admin.dto.req.ApiRegistryReq;
import cc.ewell.dubbo.admin.dubbo.service.ApiRegistryDubboService;
import cc.ewell.dubbo.admin.listener.DynamicRegistryServerSync;
import cc.ewell.dubbo.admin.registry.test.RegistryTest;
import cc.ewell.dubbo.admin.service.ApiRegistryService;
import cc.ewell.dubbo.admin.service.impl.InstanceRegistryCache;
import cc.ewell.dubbo.admin.service.impl.InterfaceRegistryCache;
import cn.hutool.extra.spring.SpringUtil;
import co.faao.plugin.transmission.EmrCodeUtils;
import co.faao.plugin.transmission.request.MessageHeader;
import co.faao.plugin.transmission.response.MessageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

/**
 * @author xushiling
 * @description 注册中心
 * @createDate 2021/11/22 11:18 上午
 */
@Api(value = "注册中心", tags = "注册中心")
@DubboService(version = "2.0.0")
public class ApiRegistryDubboServiceImpl implements ApiRegistryDubboService {


    @Autowired
    ApiRegistryService apiRegistryService;

    @ApiOperation(value = "添加注册中心", notes = "添加注册中心")
    @Override
    public MessageResult addRegistryCenter(MessageHeader header) {
        try {
            header.checkparm("registryUrl");
            ApiRegistryReq ApiRegistryReq = header.parseObject(ApiRegistryReq.class);
            apiRegistryService.insertRegistryCenter(ApiRegistryReq);
            return EmrCodeUtils.toSuccess("GATEWAY00002");
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }


    @ApiOperation(value = "修改注册中心", notes = "注册中心")
    @Override
    public MessageResult updateRegistryCenter(MessageHeader header) {
        try {
            //检测必需的参数是否存在
            header.checkparm("registryId");
            //从入参获取业务数据
            ApiRegistryReq apiRegistryReq = header.parseObject(ApiRegistryReq.class);
            apiRegistryService.updateRegistryCenter(apiRegistryReq);
            //返回正常数据
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002");
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }

    @ApiOperation(value = "查询注册中心", notes = "注册中心")
    @Override
    public MessageResult selectRegistryCenterList(MessageHeader header) {
        try {
            ApiRegistryReq RegistryCenterDomain = new ApiRegistryReq();
            List<ApiRegistryReq> list = apiRegistryService.selectRegistryCenterList(RegistryCenterDomain, null);
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002", list);
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }

    @ApiOperation(value = "删除注册中心", notes = "注册中心")
    @Override
    public MessageResult deleteRegistryCenter(MessageHeader header) {
        try {
            String registryId = header.parsekey("registryId");
            apiRegistryService.deleteRegistryCenter(registryId);
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002");
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }

    @ApiOperation(value = "获取注册中心", notes = "注册中心")
    @Override
    public MessageResult getRegistryCenter(MessageHeader header) {
        try {
            String registryId = header.parsekey("registryId");
            ApiRegistryReq apiRegistryReq = apiRegistryService.getRegistryCenter(registryId);
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002", apiRegistryReq);
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }

    @ApiOperation(value = "测试注册中心", notes = "注册中心")
    @Override
    public MessageResult testRegistryCenter(MessageHeader header) {
        URL registryUrl = URL.valueOf(header.parsekey("registryUrl"));
        RegistryTest registryTest = ExtensionLoader.getExtensionLoader(RegistryTest.class).getExtension(registryUrl.getProtocol());
        registryTest.init(registryUrl);
        return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002");
    }


    @ApiOperation(value = "切换注册中心", notes = "切换注册中心")
    @Override
    public void changeRegistryCenter(String registryAddress) {
        DynamicRegistryServerSync dynamicRegistryServerSync = SpringUtil.getBean(DynamicRegistryServerSync.class);
        dynamicRegistryServerSync.destroy();
        InstanceRegistryCache instanceRegistryCache = SpringUtil.getBean(InstanceRegistryCache.class);
        instanceRegistryCache.removeRegistryCache();

        InterfaceRegistryCache interfaceRegistryCache = SpringUtil.getBean(InterfaceRegistryCache.class);
        interfaceRegistryCache.removeRegistryCache();
        dynamicRegistryServerSync.startSubscribe(registryAddress);
        ApiRegistryReq apiRegistryReq = new ApiRegistryReq();
        apiRegistryReq.setRegistryId("1");
        apiRegistryReq.setRegistryUrl(registryAddress);
        apiRegistryService.updateRegistryCenter(apiRegistryReq);
    }

}
