package cc.ewell.dubbo.admin.dubbo.service;

import cc.ewell.dubbo.admin.dto.req.ApiServiceTestReq;
import co.faao.plugin.transmission.request.MessageHeader;
import co.faao.plugin.transmission.response.MessageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xushiling
 * @description 服务信息服务
 * @createDate 2021/11/22 11:07 上午
 */
@Api(value = "服务信息服务", tags = "服务信息服务")
public interface ApiServiceDubboService {
    /**
     * 查询所有服务
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "查询所有服务", notes = "服务信息")
    MessageResult allServices(MessageHeader header);

    /**
     * 查询所有应用
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "查询所有应用", notes = "服务信息")
    MessageResult allApplications(MessageHeader header);

    /**
     * 查询元数据信息
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "服务详情", notes = "服务信息")
    MessageResult methodList(MessageHeader header);

    /**
     * 方法参数信息
     *
     * @return
     */
    @ApiOperation(value = "方法参数信息", notes = "方法参数信息")
    MessageResult methodParams(String application, String service, String method);


    /**
     * 服务测试
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "服务测试", notes = "服务信息")
    MessageResult testMethod(ApiServiceTestReq header);
}
