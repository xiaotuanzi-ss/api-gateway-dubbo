package cc.ewell.example.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Infra
 * @date 2022-01-04
 * @description 如果请求和响应实体结构一致，可复用Req实体作为响应的实体
 */
@Data
@ApiModel(description = "示例请求实体")
public class DemoOrderReq {
	@ApiModelProperty(value = "订单ID")
	private String orderId;
	
	@ApiModelProperty(value = "订单代码", required = true)
	private String orderCode;
	
	@ApiModelProperty(value = "订单名称", required = true)
	private String orderName;
	
	@ApiModelProperty(value = "创建时间")
	private String updateDate;
	
	@ApiModelProperty(value = "操作者")
	private String operatorCode;
	
	@ApiModelProperty(value = "商品明细")
	List<DemoOrderDetailReq> demoOrderDetailReqList;
}