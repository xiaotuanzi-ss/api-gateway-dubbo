package cc.ewell.dubbo.admin.service.impl;

import cc.ewell.dubbo.admin.dto.req.ApiAccessReq;
import cc.ewell.dubbo.admin.mapper.ApiAccessMapper;
import cc.ewell.dubbo.admin.mapper.domain.ApiAccessDomain;
import cc.ewell.dubbo.admin.service.ApiAccessService;
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
public class ApiAccessServiceImpl extends BaseBiz<ApiAccessMapper, ApiAccessDomain> implements ApiAccessService {
    @Override
    public void insertApiAccess(ApiAccessReq apiAccessReq) {
        ApiAccessDomain apiAccessDomain = new ApiAccessDomain();
        BeanUtil.copyProperties(apiAccessReq, apiAccessDomain);
        apiAccessDomain.setAccessId(CommonKeyUtil.generString());
        mapper.insert(apiAccessDomain);
    }

    @Override
    public void updateApiAccess(ApiAccessReq ApiAccessReq) {
        ApiAccessDomain ApiAccessDomain = new ApiAccessDomain();
        BeanUtil.copyProperties(ApiAccessReq, ApiAccessDomain);
        mapper.updateByPrimaryKeySelective(ApiAccessDomain);
    }

    @Override
    public void updateApiAccessStatus(ApiAccessReq ApiAccessReq) {
        ApiAccessDomain ApiAccessDomain = new ApiAccessDomain();
        BeanUtil.copyProperties(ApiAccessReq, ApiAccessDomain);
        mapper.updateByPrimaryKeySelective(ApiAccessDomain);
    }

    @Override
    public void deleteApiAccess(String id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ApiAccessReq> selectApiAccessList(ApiAccessReq ApiAccessReq, RowBounds rowBounds) {
        List<ApiAccessDomain> ApiAccessDomains = null;
        Example example = new Example(ApiAccessDomain.class);
        Example.Criteria criteria = example.createCriteria();
        if (ApiAccessReq != null) {
            if (!StringUtils.isEmpty(ApiAccessReq.getAccessName())) {
                criteria.andEqualTo("accessName", ApiAccessReq.getAccessName());
            }
        }
        example.setOrderByClause("accessId");
        if (rowBounds == null) {
            ApiAccessDomains = mapper.selectByExample(example);
        } else {
            ApiAccessDomains = mapper.selectByExampleAndRowBounds(example, rowBounds);
        }
        List<ApiAccessReq> allList = BeanUtil.copyToList(ApiAccessDomains, ApiAccessReq.class);
        return allList;
    }

    @Override
    public ApiAccessReq getApiAccess(String id) {
        ApiAccessDomain domain = mapper.selectByPrimaryKey(id);
        ApiAccessReq req = new ApiAccessReq();
        BeanUtil.copyProperties(domain, req);
        return req;
    }
}
