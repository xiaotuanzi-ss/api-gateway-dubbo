package cc.ewell.example.mapper.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "DEMO_ORDER")
public class DemoOrderDomain {
    @Id
    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name = "ORDER_CODE")
    private String orderCode;

    @Column(name = "ORDER_NAME")
    private String orderName;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "OPERATOR_CODE")
    private String operatorCode;

    /**
     * @return ORDER_ID
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return ORDER_CODE
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * @param orderCode
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * @return ORDER_NAME
     */
    public String getOrderName() {
        return orderName;
    }

    /**
     * @param orderName
     */
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    /**
     * @return UPDATE_DATE
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return OPERATOR_CODE
     */
    public String getOperatorCode() {
        return operatorCode;
    }

    /**
     * @param operatorCode
     */
    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }
}