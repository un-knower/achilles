package com.quancheng.achilles.dao.modelwrite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "out_approve_time_detail")
public class FranchiseRestApproveDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String uuid;
    @ApiModelProperty(value = "餐厅id")
    @Column(name = "id")
    private String id;
    @ApiModelProperty(value = "审批时长(1-30 , 31)")
    @Column(name = "approved_long")
    private String approvedLong;
    @ApiModelProperty(value = "通道类型")
    @Column(name = "apply_type_display")
    private String applyTypeDisplay;
    @ApiModelProperty(value = "餐厅名")
    @Column(name = "rest_name")
    private String restName;
    @ApiModelProperty(value = "地址")
    @Column(name = "address")
    private String address;
    @ApiModelProperty(value = "城市")
    @Column(name = "city")
    private String city;
    @ApiModelProperty(value = "审批完成时间")
    @Column(name = "approved_time")
    private String approvedTime;
    @ApiModelProperty(value = "通道类型")
    @Column(name = "apply_type")
    private String applyType;
    @ApiModelProperty(value = "申请时间")
    @Column(name = "created_at")
    private String createdAt;
    @ApiModelProperty(value = "审核说明")
    @Column(name = "approve_remark")
    private String approveRemark;
    @ApiModelProperty(value = "高德id")
    @Column(name = "gould_id")
    private String gouldId;
    @ApiModelProperty(value = "申请者id")
    @Column(name = "user_id")
    private String userId;
    @ApiModelProperty(value = "申请者姓名")
    @Column(name = "user_name")
    private String userName;
    @ApiModelProperty(value = "申请者工号")
    @Column(name = "job_num")
    private String jobNum;
    @ApiModelProperty(value = "申请者邮箱")
    @Column(name = "email")
    private String email;
    @ApiModelProperty(value = "审批负责人")
    @Column(name = "owner")
    private String owner;
    @ApiModelProperty(value = "特许审核结果")
    @Column(name = "tx_status")
    private String txStatus;
    @ApiModelProperty(value = "线上审核结果")
    @Column(name = "online_status")
    private String online_status;
    @ApiModelProperty(value = "最终审核结果")
    @Column(name = "status")
    private String status;
    @ApiModelProperty(value = "特许拒绝原因")
    @Column(name = "tx_reason")
    private String txReason;
    @ApiModelProperty(value = "线上禁用原因")
    @Column(name = "online_reason")
    private String onlineReason;
    @ApiModelProperty(value = "拒绝原因")
    @Column(name = "reason")
    private String reason;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getApprovedLong() {
        return approvedLong;
    }
    public void setApprovedLong(String approvedLong) {
        this.approvedLong = approvedLong;
    }
    public String getApplyTypeDisplay() {
        return applyTypeDisplay;
    }
    public void setApplyTypeDisplay(String applyTypeDisplay) {
        this.applyTypeDisplay = applyTypeDisplay;
    }
    public String getRestName() {
        return restName;
    }
    public void setRestName(String restName) {
        this.restName = restName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getApprovedTime() {
        return approvedTime;
    }
    public void setApprovedTime(String approvedTime) {
        this.approvedTime = approvedTime;
    }
    public String getApplyType() {
        return applyType;
    }
    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getApproveRemark() {
        return approveRemark;
    }
    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark;
    }
    public String getGouldId() {
        return gouldId;
    }
    public void setGouldId(String gouldId) {
        this.gouldId = gouldId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getJobNum() {
        return jobNum;
    }
    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getTxStatus() {
        return txStatus;
    }
    public void setTxStatus(String txStatus) {
        this.txStatus = txStatus;
    }
    public String getOnline_status() {
        return online_status;
    }
    public void setOnline_status(String online_status) {
        this.online_status = online_status;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getTxReason() {
        return txReason;
    }
    public void setTxReason(String txReason) {
        this.txReason = txReason;
    }
    public String getOnlineReason() {
        return onlineReason;
    }
    public void setOnlineReason(String onlineReason) {
        this.onlineReason = onlineReason;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
}
