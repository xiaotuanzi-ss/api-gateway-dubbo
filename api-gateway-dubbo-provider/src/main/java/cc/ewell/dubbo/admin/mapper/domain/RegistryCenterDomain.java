package cc.ewell.dubbo.admin.mapper.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

/**
 * @author xushiling
 * @description 注册中心
 * @createDate 2022/3/11 10:38 上午
 */
@Data
@Table(name = "API_REGISTRY_CENTER")
public class RegistryCenterDomain {
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
    private Date createTime;

    @ApiModelProperty(value = "创建用户")
    @Column(name = "CREATE_USER")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @ApiModelProperty(value = "更新用户")
    @Column(name = "UPDATE_USER")
    private String updateUser;

}