package cc.ewell.dubbo.admin.service;

import cc.ewell.dubbo.admin.dto.req.ApiAccessReq;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author: xushiling
 * @description: 接入管理
 * @date: 2021/11/19 4:33 下午
 */
public interface ApiAccessService {

    void insertApiAccess(ApiAccessReq apiAccessReq);

    void updateApiAccess(ApiAccessReq apiAccessReq);

    void updateApiAccessStatus(ApiAccessReq apiAccessReq);

    void deleteApiAccess(String id);

    List<ApiAccessReq> selectApiAccessList(ApiAccessReq apiAccessReq, RowBounds rowBounds);

    ApiAccessReq getApiAccess(String id);


}
