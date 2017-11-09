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
@Table(name = "input_restaurant_account_info")
public class InputRestaurantAccountInfo {
    @Id
    @Column(name = "order_num")
    private String orderNum;
    
    @Column(name = "bank_name")
    private String bankName;
    
    @Column(name = "account_num")
    private String accountNum;
    
    @Column(name = "bank_account_name")
    private String bankAccountName;
    
    @Column(name = "account_type")
    private String accountType;
    
    @Column(name = "manage_type")
    private String manageType;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getManageType() {
        return manageType;
    }

    public void setManageType(String manageType) {
        this.manageType = manageType;
    }
}
