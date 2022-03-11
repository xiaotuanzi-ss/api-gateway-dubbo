package cc.ewell.dubbo.admin.dubbo.service;

import co.faao.plugin.transmission.request.MessageHeader;
import co.faao.plugin.transmission.response.MessageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xushiling
 * @description 接入管理服务
 * @createDate 2021/11/22 11:07 上午
 */
@Api(value = "接入管理服务", tags = "接入管理服务")
public interface ApiAccessDubboService {
    /**
     * 插入接入管理
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "新增接入管理", notes = "接入管理")
    MessageResult addApiAccess(MessageHeader header);

    /**
     * 修改接入管理
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "修改接入管理", notes = "接入管理")
    MessageResult updateApiAccess(MessageHeader header);

    /**
     * 修改接入管理状态
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "修改接入管理状态", notes = "接入管理")
    MessageResult updateApiAccessStatus(MessageHeader header);

    /**
     * 查询接入管理
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "查询接入管理", notes = "接入管理")
    MessageResult selectApiAccessList(MessageHeader header);


    /**
     * 删除接入管理
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "删除接入管理", notes = "接入管理")
    MessageResult deleteApiAccess(MessageHeader header);


    /**
     * 查看接入管理详情
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "获取接入详情", notes = "接入管理")
    MessageResult getApiAccess(MessageHeader header);

}
