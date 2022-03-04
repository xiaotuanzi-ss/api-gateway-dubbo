package cc.ewell.example.service.dubbo;

import cc.ewell.example.dto.req.DemoOrderDetailReq;
import cc.ewell.example.dto.req.DemoOrderReq;
import co.faao.plugin.page.PageHeader;
import co.faao.plugin.transmission.request.MessageHeader;
import co.faao.plugin.transmission.response.MessageResult;
import com.deepoove.swagger.dubbo.annotations.ApiRequests;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Infra
 * @date 2022-01-04
 * @description
 */
@Api(value = "示例服务", position =1)
public interface DemoOrderDubboService {
	/**
	 * 插入示例数据
	 * @param header
	 * @return
	 */
	@ApiOperation(value = "新增示例数据", notes = "新增示例数据")
	@ApiRequests(id=1, value = { DemoOrderReq.class })
	MessageResult insertDemoOrder(MessageHeader header);
	
	/**
	 * 修改示例数据
	 * @param header
	 * @return
	 */
	@ApiOperation(value = "修改示例数据", notes = "修改示例数据")
	@ApiRequests(id=2, value = { DemoOrderReq.class })
	MessageResult updateDemoOrder(MessageHeader header);
	
	/**
	 * 删除示例数据
	 * @param header
	 * @return
	 */
	@ApiOperation(value = "删除示例数据", notes = "删除示例数据")
	@ApiRequests(id=3, value = { DemoOrderReq.class })
	MessageResult deleteDemoOrder(MessageHeader header);
	
	/**
	 * 获取示例数据
	 * @param header
	 * @return
	 */
	@ApiOperation(value = "查询示例数据", notes = "查询示例数据", responseContainer = "list", response = DemoOrderReq.class)
	@ApiRequests(id=4, value = { DemoOrderReq.class })
	MessageResult getDemoOrderList(MessageHeader header);
	
	/**
	 * 分页获取示例数据
	 * @param header
	 * @return
	 */
	@ApiOperation(value = "分页查询示例数据", notes = "分页查询示例数据", responseContainer = "list", response = DemoOrderReq.class)
	@ApiRequests(id=5, value = { DemoOrderReq.class })
	MessageResult getDemoOrderListByPage(PageHeader header);
	
	/**
	 * 获取示例明细数据
	 * @param header
	 * @return
	 */
	@ApiOperation(value = "获取示例明细数据", notes = "获取示例明细数据", responseContainer = "list", response = DemoOrderDetailReq.class)
	@ApiRequests(id=6, value = { DemoOrderDetailReq.class })
	MessageResult getDemoOrderDetail(MessageHeader header);
}
