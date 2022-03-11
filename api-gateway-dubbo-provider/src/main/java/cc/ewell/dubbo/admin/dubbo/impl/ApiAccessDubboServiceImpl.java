package cc.ewell.dubbo.admin.dubbo.impl;

import cc.ewell.dubbo.admin.dto.req.ApiAccessReq;
import cc.ewell.dubbo.admin.dubbo.service.ApiAccessDubboService;
import cc.ewell.dubbo.admin.service.ApiAccessService;
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
 * @description 接入管理
 * @createDate 2021/11/22 11:18 上午
 */
@Api(value = "接入管理", tags = "接入管理")
@DubboService(version = "2.0.0")
public class ApiAccessDubboServiceImpl implements ApiAccessDubboService {


    @Autowired
    ApiAccessService apiAccessService;

    @ApiOperation(value = "添加接入管理", notes = "添加接入管理")
    @Override
    public MessageResult addApiAccess(MessageHeader header) {
        try {
            header.checkparm("registryUrl");
            ApiAccessReq ApiAccessReq = header.parseObject(ApiAccessReq.class);
            apiAccessService.insertApiAccess(ApiAccessReq);
            return EmrCodeUtils.toSuccess("GATEWAY00002");
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }


    @ApiOperation(value = "修改接入管理", notes = "修改接入管理")
    @Override
    public MessageResult updateApiAccess(MessageHeader header) {
        try {
            //检测必需的参数是否存在
            header.checkparm("accessId");
            //从入参获取业务数据
            ApiAccessReq apiAccessReq = header.parseObject(ApiAccessReq.class);
            apiAccessService.updateApiAccess(apiAccessReq);
            //返回正常数据
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002");
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }


    @ApiOperation(value = "修改接入管理", notes = "修改接入管理")
    @Override
    public MessageResult updateApiAccessStatus(MessageHeader header) {
        try {
            //检测必需的参数是否存在
            header.checkparm("accessId", "status");
            //从入参获取业务数据
            ApiAccessReq apiAccessReq = header.parseObject(ApiAccessReq.class);
            apiAccessService.updateApiAccess(apiAccessReq);
            //返回正常数据
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002");
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }

    @ApiOperation(value = "查询接入管理列表", notes = "查询接入管理列表")
    @Override
    public MessageResult selectApiAccessList(MessageHeader header) {
        try {
            ApiAccessReq ApiAccessDomain = new ApiAccessReq();
            List<ApiAccessReq> list = apiAccessService.selectApiAccessList(ApiAccessDomain, null);
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002", list);
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }

    @ApiOperation(value = "删除接入管理", notes = "删除接入管理")
    @Override
    public MessageResult deleteApiAccess(MessageHeader header) {
        try {
            String registryId = header.parsekey("accessId");
            apiAccessService.deleteApiAccess(registryId);
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002");
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }

    @ApiOperation(value = "获取接入详情", notes = "获取接入详情")
    @Override
    public MessageResult getApiAccess(MessageHeader header) {
        try {
            String registryId = header.parsekey("accessId");
            ApiAccessReq apiAccessReq = apiAccessService.getApiAccess(registryId);
            return EmrCodeUtils.to(new HashMap<>(), "GATEWAY00002", apiAccessReq);
        } catch (Exception e) {
            return EmrCodeUtils.to(e);
        }
    }

}
