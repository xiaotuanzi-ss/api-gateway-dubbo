package cc.ewell.example.service.business.impl;

import cc.ewell.example.dto.req.DemoOrderDetailReq;
import cc.ewell.example.dto.req.DemoOrderReq;
import cc.ewell.example.mapper.DemoOrderDetailMapper;
import cc.ewell.example.mapper.DemoOrderMapper;
import cc.ewell.example.mapper.domain.DemoOrderDetailDomain;
import cc.ewell.example.mapper.domain.DemoOrderDomain;
import cc.ewell.example.service.business.DemoOrderService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import co.faao.plugin.key.CommonKeyUtil;
import co.faao.plugin.starter.mybatis.other.base.BaseBiz;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Infra
 * @date 2022-01-04
 * @description
 */
@Service
public class DemoOrderServiceImpl extends BaseBiz<DemoOrderMapper, DemoOrderDomain> implements DemoOrderService {
	
	@Resource
	DemoOrderDetailMapper detailMapper;
	
	@Override
	@Transactional
	public void insertDemoOrder(DemoOrderReq demoOrderReq) {
		DemoOrderDomain demoOrderDomain = new DemoOrderDomain();
		BeanUtil.copyProperties(demoOrderReq, demoOrderDomain, "demoOrderDetailReqList");
		demoOrderDomain.setOrderId(CommonKeyUtil.generString());
		demoOrderDomain.setUpdateDate(new Date());
		mapper.insert(demoOrderDomain);
		if (CollectionUtil.isNotEmpty(demoOrderReq.getDemoOrderDetailReqList())) {
			demoOrderReq.getDemoOrderDetailReqList().stream().forEach(x -> {
				DemoOrderDetailDomain detailDomain = new DemoOrderDetailDomain();
				BeanUtil.copyProperties(x, detailDomain);
				detailDomain.setItemId(CommonKeyUtil.generString());
				detailDomain.setUpdateDate(new Date());
				detailDomain.setOrderId(demoOrderDomain.getOrderId());
				detailMapper.insert(detailDomain);
			});
		}
	}
	
	@Override
	@Transactional
	public void updateDemoOrder(DemoOrderReq demoOrderReq) {
		DemoOrderDomain demoOrderDomain = new DemoOrderDomain();
		BeanUtil.copyProperties(demoOrderReq, demoOrderDomain, "demoOrderDetailReqList");
		demoOrderDomain.setUpdateDate(new Date());
		mapper.updateByPrimaryKeySelective(demoOrderDomain);
		if (CollectionUtil.isNotEmpty(demoOrderReq.getDemoOrderDetailReqList())) {
			demoOrderReq.getDemoOrderDetailReqList().stream().forEach(x -> {
				DemoOrderDetailDomain detailDomain = new DemoOrderDetailDomain();
				BeanUtil.copyProperties(x, detailDomain);
				detailDomain.setUpdateDate(new Date());
				detailMapper.updateByPrimaryKeySelective(detailDomain);
			});
		}
	}
	
	@Override
	@Transactional
	public void deleteDemoOrder(String demoOrderId) {
		Example example = new Example(DemoOrderDetailDomain.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("orderId", demoOrderId);
		List<DemoOrderDetailDomain> detailDomainList = detailMapper.selectByExample(example);
		mapper.deleteByPrimaryKey(demoOrderId);
		if (CollectionUtil.isNotEmpty(detailDomainList)) {
			detailDomainList.stream().forEach(x -> {
				detailMapper.deleteByPrimaryKey(x.getItemId());
			});
		}
	}
	
	@Override
	public int getDemoOrderListCount(DemoOrderReq demoOrderReq) {
		Example example = createDemoOrderListExample(demoOrderReq);
		return mapper.selectCountByExample(example);
	}
	
	@Override
	public List<DemoOrderReq> getDemoOrderList(DemoOrderReq demoOrderReq, RowBounds rowBounds) {
		List<DemoOrderDomain> demoOrderDomainList = null;
		Example example = createDemoOrderListExample(demoOrderReq);
		if (rowBounds == null) {
			demoOrderDomainList = mapper.selectByExample(example);
		} else {
			demoOrderDomainList = mapper.selectByExampleAndRowBounds(example, rowBounds);
		}
		return demoOrderDomainList.stream().map(x -> {
			DemoOrderReq req = new DemoOrderReq();
			BeanUtil.copyProperties(x, req);
			return req;
		}).collect(Collectors.toList());
	}
	
	@Override
	public List<DemoOrderDetailReq> getDemoOrderDetail(DemoOrderDetailReq detailReq) {
		Example example = new Example(DemoOrderDetailDomain.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("orderId", detailReq.getOrderId());
		List<DemoOrderDetailDomain> detailDomainList = detailMapper.selectByExample(example);
		return detailDomainList.stream().map(x -> {
			DemoOrderDetailReq req = new DemoOrderDetailReq();
			BeanUtil.copyProperties(x, req);
			return req;
		}).collect(Collectors.toList());
	}
	
	private Example createDemoOrderListExample(DemoOrderReq demoOrderReq) {
		Example example = new Example(DemoOrderDomain.class);
		Example.Criteria criteria = example.createCriteria();
		if (!StringUtils.isEmpty(demoOrderReq.getOrderId())) {
			criteria.andEqualTo("orderId", demoOrderReq.getOrderId());
		}
		if (!StringUtils.isEmpty(demoOrderReq.getOrderCode())) {
			criteria.andEqualTo("orderCode", demoOrderReq.getOrderCode());
		}
		if (!StringUtils.isEmpty(demoOrderReq.getOrderName())) {
			criteria.andLike("orderName", "%" + demoOrderReq.getOrderName() + "%");
		}
		example.setOrderByClause("ORDER_CODE");
		return example;
	}
}
