package com.quancheng.achilles.dao.modelwrite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "out_app_restaurant_reported_wrong")
public class AppRestaurantReportedWrong {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @ApiModelProperty(value = "餐厅反馈表id")
    @Column(name = "feedback_id")
    private String feedbackId;
    @ApiModelProperty(value = "企业")
    @Column(name = "enterprise_name")
    private String title;
    
    @ApiModelProperty(value = "企业短号")
    @Column(name = "enterprise_name_num")
    private String enterpriseNameNum;
    
    @Column(name = "error_man")
    private String errorMan;
    
    @ApiModelProperty(value = "纠错人Id")
    @Column(name = "error_man_id")
    private String username;
    
    @ApiModelProperty(value = "纠错人姓名")
    @Column(name = "error_man_name")
    private String realname;
    
    @ApiModelProperty(value = "纠错时间")
    @Column(name = "create_time")
    private String createTime;
    @ApiModelProperty(value = "纠错人手机")
    @Column(name = "mobile")
    private String mobile;
    @ApiModelProperty(value = "餐厅ID")
    @Column(name = "restaurant_id")
    private String restaurantId;
    @ApiModelProperty(value = "餐厅名称")
    @Column(name = "store_name")
    private String storeName;
    @ApiModelProperty(value = "城市ID")
    @Column(name = "city_id")
    private String city;
    @ApiModelProperty(value = "纠错内容")
    @Column(name = "error_content")
    private String content;
    @ApiModelProperty(value = "销售Id")
    @Column(name = "bd_id")
    private String bdId;
    
    @ApiModelProperty(value = "BD维护销售")
    @Column(name = "bd_name")
    private String saleUserName;
    
    @ApiModelProperty(value = "城市")
    @Column(name = "city_name")
    private String cityName;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRealname() {
        return realname;
    }
    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSaleUserName() {
        return saleUserName;
    }
    public void setSaleUserName(String saleUserName) {
        this.saleUserName = saleUserName;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getFeedbackId() {
        return feedbackId;
    }
    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }
    public String getEnterpriseNameNum() {
        return enterpriseNameNum;
    }
    public void setEnterpriseNameNum(String enterpriseNameNum) {
        this.enterpriseNameNum = enterpriseNameNum;
    }
    public String getErrorMan() {
        return errorMan;
    }
    public void setErrorMan(String errorMan) {
        this.errorMan = errorMan;
    }
    public String getBdId() {
        return bdId;
    }
    public void setBdId(String bdId) {
        this.bdId = bdId;
    }
}
