package com.quancheng.achilles.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
@Entity
@Table(name = "v_inn_restaurant_company_user_recom")
public class RestaurantRecommender implements Serializable{
    private static final long serialVersionUID = 7540041797491808531L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    @Column(name="city_name")
    @ApiModelProperty(value = "城市名")
    private String cityName;
    
    
    @Column(name="restaurant_name")
    @ApiModelProperty(value = "餐厅名")
    private String restaurantName;
    
    
    @Column(name="area_name")
    @ApiModelProperty(value = "行政区域")
    private String areaName;
    
    @Column(name="restaurant_address")
    @ApiModelProperty(value = "餐厅地址")
    private String restaurantAddress;
    
    @Column(name="recommand_method")
    @ApiModelProperty(value = "推荐方式")
    private String recommandMethod;
    
    @Column(name="recommand_user")
    @ApiModelProperty(value = "推荐人")
    private String recommandUser;
    
    @Column(name="email")
    @ApiModelProperty(value = "推荐人邮箱")
    private String email;
    
    @Column(name="job_num")
    @ApiModelProperty(value = "推荐人工号")
    private String jobNum;
    
    
    @Column(name="recommend_company")
    @ApiModelProperty(value = "推荐企业")
    private String recommendCompany;
    
    
    @Column(name="created_at")
    @ApiModelProperty(value = "推荐时间")
    private String createdAt;
    
    public String getRecommandUser() {
        return recommandUser;
    }
    public void setRecommandUser(String recommandUser) {
        this.recommandUser = recommandUser;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getRestaurantName() {
        return restaurantName;
    }
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    public String getRecommandMethod() {
        return recommandMethod;
    }
    public void setRecommandMethod(String recommandMethod) {
        this.recommandMethod = recommandMethod;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getJobNum() {
        return jobNum;
    }
    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }
    public String getRecommendCompany() {
        return recommendCompany;
    }
    public void setRecommendCompany(String recommendCompany) {
        this.recommendCompany = recommendCompany;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getRestaurantAddress() {
        return restaurantAddress;
    }
    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }
    
}
