package cc.ewell.example.service.business;

import cc.ewell.example.dto.req.DemoOrderDetailReq;
import cc.ewell.example.dto.req.DemoOrderReq;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author Infra
 * @date 2022-01-04
 * @description
 */
public interface DemoOrderService {
	void insertDemoOrder(DemoOrderReq demoOrderReq);
	
	void updateDemoOrder(DemoOrderReq demoOrderReq);
	
	void deleteDemoOrder(String demoOrderId);
	
	int getDemoOrderListCount(DemoOrderReq demoOrderReq);
	
	List<DemoOrderReq> getDemoOrderList(DemoOrderReq demoOrderReq, RowBounds rowBounds);
	
	List<DemoOrderDetailReq> getDemoOrderDetail(DemoOrderDetailReq detailReq);
}