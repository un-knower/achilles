package com.quancheng.achilles.dao.model;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author lijun jiang
 * @version 创建时间：2016年9月8日上午10:05:30
 */
@Entity
@Table(name = "tmp_flecheck_record")
public class QuanlityCheckRecord implements Serializable {

    private static final long serialVersionUID = -1719191623630472940L;
    /**
     * 只读，不需要生成Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String            id;
    /** 检查日期 */
    @Column(name = "check_date")
    @ApiModelProperty(value = "检查日期")
    private String              checkDate;

    /** 城市 */
    @Column(name = "city_name")
    @ApiModelProperty(value = "城市")
    private String            cityName;

    /** 餐厅名称 */
    @Column(name = "shangpu_name")
    @ApiModelProperty(value = "餐厅名称")
    private String            shangpuName;

    /** 餐厅地址 */
    @Column(name = "shangpu_address")
    @ApiModelProperty(value = "餐厅地址")
    private String            shangpuAddress;

    /** 订单人姓名 */
    @Column(name = "realname")
    @ApiModelProperty(value = "订单人姓名")
    private String            realname;

    /** 订单人工号 */
    @Column(name = "job_num")
    @ApiModelProperty(value = "订单人工号")
    private String            jobNum;
    /** 订单人工号 */
    @Column(name = "order_money")
    @ApiModelProperty(value = "订单金额")
    private String orderMoney;
    /** 检查方式 */
    @Column(name = "check_mode")
    @ApiModelProperty(value = "检查方式")
    private String            checkMode;

    /** 检查类型 */
    @Column(name = "check_type")
    @ApiModelProperty(value = "检查类型")
    private String            checkType;

    /** 订单号 */
    @Column(name = "order_num")
    @ApiModelProperty(value = "订单号")
    private String            orderNum;

    /** 飞检原因 */
    @Column(name = "check_reason")
    @ApiModelProperty(value = "飞检原因")
    private String            checkReason;

    /** 是否异常 */
    @Column(name = "status")
    @ApiModelProperty(value = "是否异常")
    private String            status;

    /** 异常内容 */
    @Column(name = "check_item")
    @ApiModelProperty(value = "异常内容")
    private String            checkItem;

    /** 所属企业 */
    @Column(name = "company")
    @ApiModelProperty(value = "所属企业")
    private String            company;

    @Transient
    /** 异常原因 */
    private String            abnormalType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getShangpuName() {
        return shangpuName;
    }

    public void setShangpuName(String shangpuName) {
        this.shangpuName = shangpuName;
    }

    public String getShangpuAddress() {
        return shangpuAddress;
    }

    public void setShangpuAddress(String shangpuAddress) {
        this.shangpuAddress = shangpuAddress;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getCheckMode() {
        return checkMode;
    }

    public void setCheckMode(String checkMode) {
        this.checkMode = checkMode;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCheckReason() {
        return checkReason;
    }

    public void setCheckReason(String checkReason) {
        this.checkReason = checkReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public String getAbnormalType() {
        ObjectMapper om = new ObjectMapper();
        try {
            Map<String, String> map = om.readValue(this.checkItem, new TypeReference<Map<String, String>>() {
            });
            return map.get("abnormal_type").toString();
        } catch (Exception e) { // NOSONAR
            // Should not happen
        }
        return abnormalType;
    }

    public void setAbnormalType(String abnormalType) {
        this.abnormalType = abnormalType;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    /** 异常原因子啊异常内容里面:abnormal_type */
}
