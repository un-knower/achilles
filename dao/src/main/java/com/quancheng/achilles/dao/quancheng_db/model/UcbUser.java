package com.quancheng.achilles.dao.quancheng_db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "16860_user_data")
public class UcbUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value="")   private Long id;
    @ApiModelProperty(value="手机号") @Column(name = "mobile") private String mobile="";
    @ApiModelProperty(value="工号") @Column(name = "job_num") private String jobNum;
    @ApiModelProperty(value="发票抬头") @Column(name = "invoice_title") private String invoiceTitle="";
    @ApiModelProperty(value="客户ID") @Column(name = "client_id") private Long clientId=0L;
    @ApiModelProperty(value="部门ID") @Column(name = "sector_id") private Long sectorId=0L;
    @ApiModelProperty(value="姓名") @Column(name = "realname") private String realname;
    @ApiModelProperty(value="") @Column(name = "created_at") private String createdAt;
    @ApiModelProperty(value="") @Column(name = "updated_at") private String updatedAt;
    @ApiModelProperty(value="") @Column(name = "deleted_at") private String deletedAt;
    @ApiModelProperty(value="用户邮箱") @Column(name = "email") private String email;
    @ApiModelProperty(value="上级邮箱") @Column(name = "superior_email") private String superiorEmail;
    @ApiModelProperty(value="上级姓名") @Column(name = "superior_name") private String superiorName;
    @ApiModelProperty(value="所在城市") @Column(name = "city") private String city;
    @ApiModelProperty(value="所在城市ID") @Column(name = "city_id") private Long cityId=0L;
    @ApiModelProperty(value="分公司ID") @Column(name = "branch_id") private Long branchId=0L;
    @ApiModelProperty(value="事业部ID") @Column(name = "businessunit_id") private Long businessunitId=0L;
    @ApiModelProperty(value="大区ID") @Column(name = "region_id") private Long regionId=0L;
    @ApiModelProperty(value="产品组ID") @Column(name = "productgroup_id") private Long productgroupId=0L;
    @ApiModelProperty(value="") @Column(name = "eEvent_id") private String eEventId;
    @ApiModelProperty(value="") @Column(name = "group_id") private Long groupId=0L;
    @ApiModelProperty(value="") @Column(name = "en_name") private String enName;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getJobNum() {
        return jobNum;
    }
    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }
    public String getInvoiceTitle() {
        return invoiceTitle;
    }
    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }
    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public Long getSectorId() {
        return sectorId;
    }
    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }
    public String getRealname() {
        return realname;
    }
    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getDeletedAt() {
        return deletedAt;
    }
    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSuperiorEmail() {
        return superiorEmail;
    }
    public void setSuperiorEmail(String superiorEmail) {
        this.superiorEmail = superiorEmail;
    }
    public String getSuperiorName() {
        return superiorName;
    }
    public void setSuperiorName(String superiorName) {
        this.superiorName = superiorName;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Long getCityId() {
        return cityId;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    public Long getBranchId() {
        return branchId;
    }
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
    public Long getBusinessunitId() {
        return businessunitId;
    }
    public void setBusinessunitId(Long businessunitId) {
        this.businessunitId = businessunitId;
    }
    public Long getRegionId() {
        return regionId;
    }
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
    public Long getProductgroupId() {
        return productgroupId;
    }
    public void setProductgroupId(Long productgroupId) {
        this.productgroupId = productgroupId;
    }
    public String geteEventId() {
        return eEventId;
    }
    public void seteEventId(String eEventId) {
        this.eEventId = eEventId;
    }
    public Long getGroupId() {
        return groupId;
    }
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    public String getEnName() {
        return enName;
    }
    public void setEnName(String enName) {
        this.enName = enName;
    }
}
