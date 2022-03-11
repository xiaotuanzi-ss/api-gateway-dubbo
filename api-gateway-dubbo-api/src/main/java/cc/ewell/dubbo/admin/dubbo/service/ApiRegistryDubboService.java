package cc.ewell.dubbo.admin.dubbo.service;

import co.faao.plugin.transmission.request.MessageHeader;
import co.faao.plugin.transmission.response.MessageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xushiling
 * @description 注册中心服务
 * @createDate 2021/11/22 11:07 上午
 */
@Api(value = "注册中心服务", tags = "注册中心服务")
public interface ApiRegistryDubboService {
    /**
     * 插入注册中心
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "新增注册中心", notes = "注册中心")
    MessageResult addRegistryCenter(MessageHeader header);

    /**
     * 修改注册中心
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "修改注册中心", notes = "注册中心")
    MessageResult updateRegistryCenter(MessageHeader header);

    /**
     * 查询注册中心
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "查询注册中心", notes = "注册中心")
    MessageResult selectRegistryCenterList(MessageHeader header);


    /**
     * 删除注册中心
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "删除注册中心", notes = "注册中心")
    MessageResult deleteRegistryCenter(MessageHeader header);


    /**
     * 查看注册中心详情
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "修改注册中心", notes = "注册中心")
    MessageResult getRegistryCenter(MessageHeader header);



    /**
     * 测试注册中心
     *
     * @param header
     * @return
     */
    @ApiOperation(value = "测试注册中心", notes = "注册中心")
    MessageResult testRegistryCenter(MessageHeader header);

    /**
     * 切换注册中心
     *
     * @param registryAddress
     * @return
     */
    @ApiOperation(value = "切换注册中心", notes = "注册中心")
    void changeRegistryCenter(String registryAddress);

}
