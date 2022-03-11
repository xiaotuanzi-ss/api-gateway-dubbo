package cc.ewell.dubbo.admin.service.impl;

import cc.ewell.dubbo.admin.dto.req.RegistryCenterReq;
import cc.ewell.dubbo.admin.mapper.RegistryCenterMapper;
import cc.ewell.dubbo.admin.mapper.domain.RegistryCenterDomain;
import cc.ewell.dubbo.admin.service.RegistryCenterService;
import cn.hutool.core.bean.BeanUtil;
import co.faao.plugin.key.CommonKeyUtil;
import co.faao.plugin.starter.mybatis.other.base.BaseBiz;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author: xushiling
 * @description:
 * @date: 2021/11/19 4:40 下午
 */
@Service
public class RegistryCenterServiceImpl extends BaseBiz<RegistryCenterMapper, RegistryCenterDomain> implements RegistryCenterService {
    @Override
    public void insertRegistryCenter(RegistryCenterReq registryCenterReq) {
        RegistryCenterDomain registryCenterDomain = new RegistryCenterDomain();
        BeanUtil.copyProperties(registryCenterReq, registryCenterDomain);
        registryCenterDomain.setRegistryId(CommonKeyUtil.generString());
        mapper.insert(registryCenterDomain);
    }

    @Override
    public void updateRegistryCenter(RegistryCenterReq RegistryCenterReq) {
        RegistryCenterDomain RegistryCenterDomain = new RegistryCenterDomain();
        BeanUtil.copyProperties(RegistryCenterReq, RegistryCenterDomain);
        mapper.updateByPrimaryKeySelective(RegistryCenterDomain);
    }

    @Override
    public void deleteRegistryCenter(String id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<RegistryCenterReq> selectRegistryCenterList(RegistryCenterReq RegistryCenterReq, RowBounds rowBounds) {
        List<RegistryCenterDomain> RegistryCenterDomains = null;
        Example example = new Example(RegistryCenterDomain.class);
        Example.Criteria criteria = example.createCriteria();
        if (RegistryCenterReq != null) {
            if (!StringUtils.isEmpty(RegistryCenterReq.getRegistryUrl())) {
                criteria.andEqualTo("registryUrl", RegistryCenterReq.getRegistryUrl());
            }
        }
        example.setOrderByClause("registryId");
        if (rowBounds == null) {
            RegistryCenterDomains = mapper.selectByExample(example);
        } else {
            RegistryCenterDomains = mapper.selectByExampleAndRowBounds(example, rowBounds);
        }
        List<RegistryCenterReq> allList = BeanUtil.copyToList(RegistryCenterDomains, RegistryCenterReq.class);
        return allList;
    }

    @Override
    public RegistryCenterReq getRegistryCenter(String id) {
        RegistryCenterDomain domain = mapper.selectByPrimaryKey(id);
        RegistryCenterReq req = new RegistryCenterReq();
        BeanUtil.copyProperties(domain, req);
        return req;
    }
}
