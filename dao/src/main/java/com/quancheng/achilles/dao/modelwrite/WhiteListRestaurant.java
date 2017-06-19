package com.quancheng.achilles.dao.modelwrite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
@Entity
@Table(name = "out_white_list_restaurant")
public class WhiteListRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @ApiModelProperty(value = "餐厅id")
    @Column(name = "rest_id")
    private String restId;
    @ApiModelProperty(value = "申请人姓名")
    @Column(name = "realname")
    private String realname;
    @ApiModelProperty(value = "申请人邮箱")
    @Column(name = "email")
    private String email;
    @ApiModelProperty(value = "申请人工号")
    @Column(name = "job_num")
    private String jobNum;
    @ApiModelProperty(value = "餐厅名")
    @Column(name = "restaurant_name")
    private String restaurantName;
    @ApiModelProperty(value = "城市")
    @Column(name = "city")
    private String city;
    @ApiModelProperty(value = "餐厅地址")
    @Column(name = "address")
    private String address;
    @ApiModelProperty(value = "审批人邮箱")
    @Column(name = "approve_user_email")
    private String approveUserEmail;
    @ApiModelProperty(value = "审批方式")
    @Column(name = "approve_method")
    private String approveMethod;
    @ApiModelProperty(value = "申请时间")
    @Column(name = "created_at")
    private String createdAt;
    @ApiModelProperty(value = "审批通过时间")
    @Column(name = "approve_time")
    private String approveTime;
    @ApiModelProperty(value = "申请企业")
    @Column(name = "title")
    private String title;
    @ApiModelProperty(value = "申请状态")
    @Column(name = "approve_status")
    private String approveStatus;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getRealname() {
        return realname;
    }
    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getJobNum() {
        return jobNum;
    }
    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }
    public String getRestaurantName() {
        return restaurantName;
    }
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
    public String getApproveUserEmail() {
        return approveUserEmail;
    }
    public void setApproveUserEmail(String approveUserEmail) {
        this.approveUserEmail = approveUserEmail;
    }
    public String getApproveMethod() {
        return approveMethod;
    }
    public void setApproveMethod(String approveMethod) {
        this.approveMethod = approveMethod;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getApproveTime() {
        return approveTime;
    }
    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }
    public String getApproveStatus() {
        return approveStatus;
    }
    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }
    public String getRestId() {
        return restId;
    }
    public void setRestId(String restId) {
        this.restId = restId;
    }
    
}
