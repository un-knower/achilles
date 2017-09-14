package com.quancheng.achilles.dao.modelwrite;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
//@Entity
//@Table(name = "tmp_inn_visit_log")
public class RestaurantVisitLog implements Serializable{
    private static final long serialVersionUID = -4328878160244212514L;
    
    @Id
    private String id;
    
    @Column(name="restaurant_id")
    @ApiModelProperty(value = "餐厅ID")
    private Long restaurantId;
    
    @Column(name="restaurant_name")
    @ApiModelProperty(value = "餐厅名")
    private String restaurantName;

    @Column(name="type")
    @ApiModelProperty(value = "餐厅来源")
    private String type;

    @Column(name="city_name")
    @ApiModelProperty(value = "城市")
    private String cityName;

    @Column(name="address")
    @ApiModelProperty(value = "餐厅地址")
    private String address;

//    @Column(name="plan_time")
//    @ApiModelProperty(value = "拜访时间")
//    private String planTime; 
    @Column(name="start_time")
    @ApiModelProperty(value = "拜访日期")
    private String startTime;
    
    @Column(name="visit_person")
    @ApiModelProperty(value = "客户姓名")
    private String visitPerson;

    @Column(name="visit_person_phone")
    @ApiModelProperty(value = "客户电话")
    private String visitPersonPhone;

    @Column(name="status")
    @ApiModelProperty(value = "餐厅状态")
    private String status;

    @Column(name="visit_content")
    @ApiModelProperty(value = "拜访内容")
    private String visitContent;

    @Column(name="prj_names")
    @ApiModelProperty(value = "项目名")
    private String projectNames;

    @Column(name="sale_name")
    @ApiModelProperty(value = "销售") 
    private String salesName;

    @Column(name="uid")
    @ApiModelProperty(value = "销售ID")
    private Long saleid;

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getVisitPerson() {
        return visitPerson;
    }

    public void setVisitPerson(String visitPerson) {
        this.visitPerson = visitPerson;
    }

    public String getVisitPersonPhone() {
        return visitPersonPhone;
    }

    public void setVisitPersonPhone(String visitPersonPhone) {
        this.visitPersonPhone = visitPersonPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVisitContent() {
        return visitContent;
    }

    public void setVisitContent(String visitContent) {
        this.visitContent = visitContent;
    }

    public String getProjectNames() {
        return projectNames;
    }

    public void setProjectNames(String projectNames) {
        this.projectNames = projectNames;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSaleid() {
        return saleid;
    }

    public void setSaleid(Long saleid) {
        this.saleid = saleid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
