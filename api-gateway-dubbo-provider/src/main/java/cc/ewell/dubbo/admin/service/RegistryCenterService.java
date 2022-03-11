package cc.ewell.dubbo.admin.service;

import cc.ewell.dubbo.admin.dto.req.RegistryCenterReq;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author: xushiling
 * @description: 注册中心
 * @date: 2021/11/19 4:33 下午
 */
public interface RegistryCenterService {

    void insertRegistryCenter(RegistryCenterReq registryCenterReq);

    void updateRegistryCenter(RegistryCenterReq registryCenterReq);

    void deleteRegistryCenter(String id);

    List<RegistryCenterReq> selectRegistryCenterList(RegistryCenterReq registryCenterReq, RowBounds rowBounds);

    RegistryCenterReq getRegistryCenter(String id);


}
