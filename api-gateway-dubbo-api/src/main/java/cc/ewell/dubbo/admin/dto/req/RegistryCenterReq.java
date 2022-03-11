package cc.ewell.dubbo.admin.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "API_REGISTRY_CENTER")
public class RegistryCenterReq implements Serializable {
    @Id
    @ApiModelProperty(value = "主键id")
    @Column(name = "REGISTRY_ID")
    private String registryId;

    @ApiModelProperty(value = "注册中心名称")
    @Column(name = "REGISTRY_NAME")
    private String registryName;

    @ApiModelProperty(value = "注册中心类型")
    @Column(name = "REGISTRY_TYPE")
    private String registryType;

    @ApiModelProperty(value = "注册中心链接")
    @Column(name = "REGISTRY_URL")
    private String registryUrl;

    @ApiModelProperty(value = "所属厂商")
    @Column(name = "VENDOR_LABEL")
    private String vendorLabel;

    @ApiModelProperty(value = "描述")
    @Column(name = "DESCRIBE")
    private String describe;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "CREATE_TIME")
    private String createTime;

    @ApiModelProperty(value = "创建用户")
    @Column(name = "CREATE_USER")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    @Column(name = "UPDATE_TIME")
    private String updateTime;

    @ApiModelProperty(value = "更新用户")
    @Column(name = "UPDATE_USER")
    private String updateUser;

}