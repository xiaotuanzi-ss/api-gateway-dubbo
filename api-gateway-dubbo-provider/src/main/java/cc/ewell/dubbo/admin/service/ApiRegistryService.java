package cc.ewell.dubbo.admin.service;

import cc.ewell.dubbo.admin.dto.req.ApiRegistryReq;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author: xushiling
 * @description: 注册中心
 * @date: 2021/11/19 4:33 下午
 */
public interface ApiRegistryService {

    void insertRegistryCenter(ApiRegistryReq apiRegistryReq);

    void updateRegistryCenter(ApiRegistryReq apiRegistryReq);

    void deleteRegistryCenter(String id);

    List<ApiRegistryReq> selectRegistryCenterList(ApiRegistryReq apiRegistryReq, RowBounds rowBounds);

    ApiRegistryReq getRegistryCenter(String id);


}
