package cc.ewell.example.dto.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Infra
 * @date 2022-01-04
 * @description 如果响应的实体比较复杂，可以额外定义Res实体来返回数据
 */
@Data
@ApiModel(description = "示例响应实体")
public class DemoOrderRes {
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
	List<DemoOrderDetailRes> demoOrderDetailResList;
	
	@ApiModelProperty(value = "子订单")
	List<DemoOrderRes> subOrderList;
}