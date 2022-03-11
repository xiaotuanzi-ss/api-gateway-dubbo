package cc.ewell.dubbo.admin.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "API_ACCESS")
public class ApiAccessReq implements Serializable {
    @Id
    @ApiModelProperty(value = "主键id")
    @Column(name = "ACCESS_ID")
    private String accessId;

    @ApiModelProperty(value = "接入名称")
    @Column(name = "ACCESS_NAME")
    private String accessName;

    @ApiModelProperty(value = "接入方式(dubbo)")
    @Column(name = "ACCESS_TYPE")
    private String accessType;

    @ApiModelProperty(value = "状态 0停用 1启用")
    @Column(name = "STATUS")
    private String status;

    @ApiModelProperty(value = "注册中心ID")
    @Column(name = "REGISTRY_ID")
    private String registryId;

    @ApiModelProperty(value = "注册中心链接")
    @Column(name = "REGISTRY_URL")
    private String registryUrl;

    @ApiModelProperty(value = "接入url(group/service:version)")
    @Column(name = "ACCESS_URL")
    private String accessUrl;

    @ApiModelProperty(value = "服务级别")
    @Column(name = "SERVICE_LEVEL")
    private String serviceLevel;

    @ApiModelProperty(value = "服务厂商")
    @Column(name = "VENDOR_LABEL")
    private String vendorLabel;

    @ApiModelProperty(value = "应用名")
    @Column(name = "APPLICATION_NAME")
    private String applicationName;

    @ApiModelProperty(value = "dubbo服务")
    @Column(name = "DUBBO_SERVICE")
    private String dubboService;

    @ApiModelProperty(value = "dubbo服务分组")
    @Column(name = "SERVICE_GROUP")
    private String serviceGroup;

    @ApiModelProperty(value = "dubbo服务版本")
    @Column(name = "SERVICE_VERSION")
    private String serviceVersion;

    @ApiModelProperty(value = "服务更新方式 0自动更新")
    @Column(name = "UPDATE_MODE")
    private String updateMode;

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