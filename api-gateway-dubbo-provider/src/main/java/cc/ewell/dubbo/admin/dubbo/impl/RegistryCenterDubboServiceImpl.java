package cc.ewell.dubbo.admin.dubbo.impl;

import cc.ewell.dubbo.admin.dto.req.RegistryCenterReq;
import cc.ewell.dubbo.admin.dubbo.service.RegistryCenterDubboService;
import cc.ewell.dubbo.admin.listener.DynamicRegistryServerSync;
import cc.ewell.dubbo.admin.service.RegistryCenterService;
import cc.ewell.dubbo.admin.service.impl.InstanceRegistryCache;
import cc.ewell.dubbo.admin.service.impl.InterfaceRegistryCache;
import cn.hutool.extra.spring.SpringUtil;
import co.faao.plugin.transmission.EmrCodeUtils;
import co.faao.plugin.transmission.request.MessageHeader;
import co.faao.plugin.transmission.response.MessageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
public class RegistryCenterDubboServiceImpl implements RegistryCenterDubboService {


    @Autowired
    RegistryCenterService registryCenterService;

    @ApiOperation(value = "添加注册中心", notes = "添加注册中心")
    @Override
    public MessageResult addRegistryCenter(MessageHeader header) {
        try {
            header.checkparm("registryUrl");
            RegistryCenterReq RegistryCenterReq = header.parseObject(RegistryCenterReq.class);
            registryCenterService.insertRegistryCenter(RegistryCenterReq);
            return EmrCodeUtils.toSuccess("GATEWAY00002");
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }


    @ApiOperation(value = "添加注册中心", notes = "添加注册中心")
    @Override
    public MessageResult updateRegistryCenter(MessageHeader header) {
        try {
            //检测必需的参数是否存在
            header.checkparm("registryId");
            //从入参获取业务数据
            RegistryCenterReq registryCenterReq = header.parseObject(RegistryCenterReq.class);
            registryCenterService.updateRegistryCenter(registryCenterReq);
            //返回正常数据
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002");
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }

    @ApiOperation(value = "添加注册中心", notes = "添加注册中心")
    @Override
    public MessageResult selectRegistryCenterList(MessageHeader header) {
        try {
            RegistryCenterReq RegistryCenterDomain = new RegistryCenterReq();
            List<RegistryCenterReq> list = registryCenterService.selectRegistryCenterList(RegistryCenterDomain, null);
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002", list);
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }

    @ApiOperation(value = "添加注册中心", notes = "添加注册中心")
    @Override
    public MessageResult deleteRegistryCenter(MessageHeader header) {
        try {
            String registryId = header.parsekey("registryId");
            registryCenterService.deleteRegistryCenter(registryId);
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002");
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }

    @ApiOperation(value = "添加注册中心", notes = "添加注册中心")
    @Override
    public MessageResult getRegistryCenter(MessageHeader header) {
        try {
            String registryId = header.parsekey("registryId");
            RegistryCenterReq registryCenterReq = registryCenterService.getRegistryCenter(registryId);
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002", registryCenterReq);
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }

    @ApiOperation(value = "添加注册中心", notes = "添加注册中心")
    @Override
    public MessageResult testRegistryCenter(MessageHeader header) {
        return null;
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
        RegistryCenterReq registryCenterReq = new RegistryCenterReq();
        registryCenterReq.setRegistryId("1");
        registryCenterReq.setRegistryUrl(registryAddress);
        registryCenterService.updateRegistryCenter(registryCenterReq);
    }

}
