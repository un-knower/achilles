package com.quancheng.achilles.dao.ds_qc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单特殊备注
 * @author zhuzhong
 */
@Entity
@Table(name = "api_order_remarks")
public class API_OrderSpecialMark {
    @Id
    @Column(name = "order_num")
    private String orderNum;
    @Column(name = "remarks")
    private String remarks;
    public String getOrderNum() {
        return orderNum;
    }
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
    public String getRemarks() {
        return remarks;
    }
    public API_OrderSpecialMark setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }
}
