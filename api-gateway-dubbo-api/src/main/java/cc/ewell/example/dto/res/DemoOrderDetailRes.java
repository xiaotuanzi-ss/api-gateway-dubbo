package cc.ewell.example.dto.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Infra
 * @date 2022-01-04
 * @description 如果响应的实体比较复杂，可以额外定义Res实体来返回数据
 */
@Data
@ApiModel(description = "示例响应实体")
public class DemoOrderDetailRes {
	@ApiModelProperty(value = "记录ID")
	private String itemId;
	
	@ApiModelProperty(value = "商品代码", required = true)
	private String itemCode;
	
	@ApiModelProperty(value = "商品名称", required = true)
	private String itemName;
	
	@ApiModelProperty(value = "创建时间")
	private String updateDate;
}