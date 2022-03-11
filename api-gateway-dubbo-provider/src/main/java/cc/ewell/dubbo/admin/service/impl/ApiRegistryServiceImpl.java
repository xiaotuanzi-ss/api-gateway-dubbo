package cc.ewell.dubbo.admin.service.impl;

import cc.ewell.dubbo.admin.dto.req.ApiRegistryReq;
import cc.ewell.dubbo.admin.mapper.ApiRegistryMapper;
import cc.ewell.dubbo.admin.mapper.domain.ApiRegistryDomain;
import cc.ewell.dubbo.admin.service.ApiRegistryService;
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
public class ApiRegistryServiceImpl extends BaseBiz<ApiRegistryMapper, ApiRegistryDomain> implements ApiRegistryService {
    @Override
    public void insertRegistryCenter(ApiRegistryReq apiRegistryReq) {
        ApiRegistryDomain apiRegistryDomain = new ApiRegistryDomain();
        BeanUtil.copyProperties(apiRegistryReq, apiRegistryDomain);
        apiRegistryDomain.setRegistryId(CommonKeyUtil.generString());
        mapper.insert(apiRegistryDomain);
    }

    @Override
    public void updateRegistryCenter(ApiRegistryReq ApiRegistryReq) {
        ApiRegistryDomain ApiRegistryDomain = new ApiRegistryDomain();
        BeanUtil.copyProperties(ApiRegistryReq, ApiRegistryDomain);
        mapper.updateByPrimaryKeySelective(ApiRegistryDomain);
    }

    @Override
    public void deleteRegistryCenter(String id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ApiRegistryReq> selectRegistryCenterList(ApiRegistryReq ApiRegistryReq, RowBounds rowBounds) {
        List<ApiRegistryDomain> apiRegistryDomains = null;
        Example example = new Example(ApiRegistryDomain.class);
        Example.Criteria criteria = example.createCriteria();
        if (ApiRegistryReq != null) {
            if (!StringUtils.isEmpty(ApiRegistryReq.getRegistryUrl())) {
                criteria.andEqualTo("registryUrl", ApiRegistryReq.getRegistryUrl());
            }
        }
        example.setOrderByClause("registryId");
        if (rowBounds == null) {
            apiRegistryDomains = mapper.selectByExample(example);
        } else {
            apiRegistryDomains = mapper.selectByExampleAndRowBounds(example, rowBounds);
        }
        List<ApiRegistryReq> allList = BeanUtil.copyToList(apiRegistryDomains, ApiRegistryReq.class);
        return allList;
    }

    @Override
    public ApiRegistryReq getRegistryCenter(String id) {
        ApiRegistryDomain domain = mapper.selectByPrimaryKey(id);
        ApiRegistryReq req = new ApiRegistryReq();
        BeanUtil.copyProperties(domain, req);
        return req;
    }
}
