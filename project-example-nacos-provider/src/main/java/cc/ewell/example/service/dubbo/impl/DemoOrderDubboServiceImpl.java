package cc.ewell.example.service.dubbo.impl;

import cc.ewell.authority.api.v3.dto.res.UserEntityRes;
import cc.ewell.example.service.dubbo.DemoOrderDubboService;
import cc.ewell.example.common.ErrorMsgConstant;
import cc.ewell.example.dto.req.DemoOrderDetailReq;
import cc.ewell.example.dto.req.DemoOrderReq;
import cc.ewell.example.service.business.DemoOrderService;
import cc.ewell.example.service.consumer.DemoAuthConsumerService;
import co.faao.plugin.page.Page;
import co.faao.plugin.page.PageHeader;
import co.faao.plugin.page.inter.Data;
import co.faao.plugin.transmission.EmrCodeUtils;
import co.faao.plugin.transmission.request.MessageHeader;
import co.faao.plugin.transmission.response.MessageResult;
import co.faao.plugin.transmission.user.User;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author Infra
 * @date 2022-01-04
 * @description
 */
@Service(interfaceClass = DemoOrderDubboService.class, version = "1.0.0")
public class DemoOrderDubboServiceImpl implements DemoOrderDubboService {
	
	@Autowired
	DemoOrderService orderService;
	
	@Resource
	DemoAuthConsumerService authConsumerService;
	
	@Override
	public MessageResult insertDemoOrder(MessageHeader header) {
		//检测必需的参数是否存在
		header.checkparm("orderCode", "orderName", "demoOrderDetailReqList");
		//从入参获取业务数据
		DemoOrderReq orderReq = header.parseObject(DemoOrderReq.class);
		//从入参获取当前操作者
		User operatorUser = header.getUser(User.class);
		orderReq.setOperatorCode(operatorUser.getStaffCode());
		//调用具体业务接口
		orderService.insertDemoOrder(orderReq);
		//返回正常数据
		return EmrCodeUtils.to(new HashMap<>(), ErrorMsgConstant.SUCCESS);
	}
	
	@Override
	public MessageResult updateDemoOrder(MessageHeader header) {
		//检测必需的参数是否存在
		header.checkparm("orderId", "orderCode", "orderName");
		//从入参获取业务数据
		DemoOrderReq orderReq = header.parseObject(DemoOrderReq.class);
		//从入参获取当前操作者
		User operatorUser = header.getUser(User.class);
		orderReq.setOperatorCode(operatorUser.getStaffCode());
		//调用具体业务接口
		orderService.updateDemoOrder(orderReq);
		//返回正常数据
		return EmrCodeUtils.to(new HashMap<>(), ErrorMsgConstant.SUCCESS);
	}
	
	@Override
	public MessageResult deleteDemoOrder(MessageHeader header) {
		//检测必需的参数是否存在
		header.checkparm("orderId");
		//从入参获取业务数据
		DemoOrderReq orderReq = header.parseObject(DemoOrderReq.class);
		//从入参获取当前操作者
		User operatorUser = header.getUser(User.class);
		orderReq.setOperatorCode(operatorUser.getStaffCode());
		//调用具体业务接口
		orderService.deleteDemoOrder(orderReq.getOrderId());
		//返回正常数据
		return EmrCodeUtils.to(new HashMap<>(), ErrorMsgConstant.SUCCESS);
	}
	
	@Override
	public MessageResult getDemoOrderList(MessageHeader header) {
		//从入参获取业务数据
		DemoOrderReq orderReq = header.parseObject(DemoOrderReq.class);
		//调用具体业务接口
		List<DemoOrderReq> demoOrderReqList = orderService.getDemoOrderList(orderReq, null);
		//返回正常数据
		return EmrCodeUtils.to(new HashMap<>(), ErrorMsgConstant.SUCCESS, demoOrderReqList);
	}
	
	@Override
	public MessageResult getDemoOrderListByPage(PageHeader header) {
		//从入参获取业务数据
		DemoOrderReq orderReq = header.parseObject(DemoOrderReq.class);
		//调用具体业务接口
		Page<DemoOrderReq> pageInfo = new Page<>(header, new Data<DemoOrderReq>() {
			@Override
			public int selectCount() {
				return orderService.getDemoOrderListCount(orderReq);
			}
			
			@Override
			public Object selectData(RowBounds rowBounds) {
				return orderService.getDemoOrderList(orderReq, rowBounds);
			}
		});
		//返回正常数据
		return EmrCodeUtils.to(new HashMap<>(), ErrorMsgConstant.SUCCESS, pageInfo);
	}
	
	@Override
	public MessageResult getDemoOrderDetail(MessageHeader header) {
		//从入参获取业务数据
		DemoOrderDetailReq detailReq = header.parseObject(DemoOrderDetailReq.class);
		//调用具体业务接口
		List<DemoOrderDetailReq> detailReqList = orderService.getDemoOrderDetail(detailReq);
		//返回正常数据
		return EmrCodeUtils.to(new HashMap<>(), ErrorMsgConstant.SUCCESS, detailReqList);
	}
	
	/**
	 * 消费其它微服务的接口示例
	 */
	private void businessDependOnUserInfo() {
		UserEntityRes userEntityRes = authConsumerService.getUserDetailByStaffCode("N001");
		//用户相关的业务操作
	}
}
