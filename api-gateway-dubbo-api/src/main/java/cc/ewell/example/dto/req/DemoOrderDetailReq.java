package cc.ewell.example.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Infra
 * @date 2022-01-04
 * @description 如果请求和响应实体结构一致，可复用Req实体作为响应的实体
 */
@Data
@ApiModel(description = "示例请求实体")
public class DemoOrderDetailReq {
	@ApiModelProperty(value = "记录ID")
	private String itemId;
	
	@ApiModelProperty(value = "商品代码", required = true)
	private String itemCode;
	
	@ApiModelProperty(value = "商品名称", required = true)
	private String itemName;
	
	@ApiModelProperty(value = "订单ID")
	private String orderId;
	
	@ApiModelProperty(value = "创建时间")
	private String updateDate;
}
