package com.quancheng.achilles.dao.ds_qc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单代收款证明
 * @author zhuzhong
 */
@Entity
@Table(name = "input_order_receipt_modify")
public class InputReceipt {
    @Id
    @Column(name = "order_num")
    private String orderNum;
    
    @Column(name = "picture_url")
    private String pictureUrl;
    
    @Column(name = "pt")
    private String pt;
    
    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }
}
