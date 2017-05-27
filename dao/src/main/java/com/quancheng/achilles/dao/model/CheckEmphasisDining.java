package com.quancheng.achilles.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author lijun jiang
 * @version 创建时间：2016年9月8日上午10:05:30
 */
@Entity
@Table(name = "tmp_check_dining")
public class CheckEmphasisDining implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1857245906628417071L;
    /**
     * 只读，不需要生成Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String            id;

    /** 餐厅名称 */
    @Column(name = "restaurant_name")
    @ApiModelProperty(value = "餐厅名称")
    private String            restaurantName;

    /** 上线时间 */
    @Column(name = "online_time")
    @ApiModelProperty(value = "上线时间")
    private String              onlineTime;

    /** 餐厅状态 */
    @Column(name = "status")
    @ApiModelProperty(value = "餐厅状态")
    private Integer           status;

    /** 关注起始日期 */
    @Column(name = "created_at")
    @ApiModelProperty(value = "关注起始日期")
    private String              createdAt;

    /** 关注结束日期 */
    @Column(name = "expire")
    @ApiModelProperty(value = "关注结束日期")
    private String              expire;

    /** 相关订单号 */
    @Column(name = "fei_case")
    @ApiModelProperty(value = "相关订单号")
    private String            feiCase;

    /** 城市 */
    @Column(name = "city_name")
    @ApiModelProperty(value = "城市")
    private String            cityName;
    /** 公司 */
    @Column(name = "company")
    @ApiModelProperty(value = "公司")
    private String            company;
    /** 订单日期 */
    @Column(name = "create_time")
    @ApiModelProperty(value = "订单日期")
    private String            createTime;

    /** 关注的状态 */
    @Transient
    @ApiModelProperty(value = "关注状态")
    private String            attentionStatus;

    /** 存档次数 */
    @Column(name = "file_number")
    @ApiModelProperty(value = "存档次数")
    private String            fileNumber;

    /** 异常内容 */
    @Column(name = "check_item")
    @ApiModelProperty(value = "异常内容")
    private String            checkItem;

    /** 检查对象 */
    @Column(name = "target_val")
    @ApiModelProperty(value = "检查对象")
    private String            targetVal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getFeiCase() {
        return feiCase;
    }

    public void setFeiCase(String feiCase) {
        this.feiCase = feiCase;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAttentionStatus() {
        return attentionStatus;
    }

    public void setAttentionStatus(String attentionStatus) {
        this.attentionStatus = attentionStatus;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public String getTargetVal() {
        return targetVal;
    }

    public void setTargetVal(String targetVal) {
        this.targetVal = targetVal;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

}
