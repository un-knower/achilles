package com.quancheng.achilles.dao.modelwrite;

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
//@Entity
//@Table(name = "tmp_check_people")
public class CheckEmphasisPeople implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3528073302454565227L;

    /**
     * 只读，不需要生成Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String            id;

    /** 用户姓名 */
    @Column(name = "name")
    @ApiModelProperty(value = "用户姓名")
    private String            name;

    /** 检查对象 */
    @Column(name = "target_val")
    @ApiModelProperty(value = "检查对象")
    private String            targetVal;

    /** 城市 */
    @Column(name = "city_name")
    @ApiModelProperty(value = "城市")
    private String            cityName;

    /** 用户工号 */
    @Column(name = "job_num")
    @ApiModelProperty(value = " 用户工号")
    private String            jobNum;

    /** 企业邮箱 */
    @Column(name = "email")
    @ApiModelProperty(value = "检查对象")
    private String            email;

    /** 关注起始日期 */
    @Column(name = "created_at")
    @ApiModelProperty(value = "关注起始日期")
    private String              createdAt;

    /** 关注结束日期 */
    @Column(name = "expire")
    @ApiModelProperty(value = "关注结束日期")
    private String              expire;

    /** 规则类型 */
    @Column(name = "title")
    @ApiModelProperty(value = "规则类型")
    private String            title;
    /** 公司 */
    @Column(name = "company")
    @ApiModelProperty(value = " 公司")
    private String            company;
    /** 订单日期 */
    @Column(name = "create_time")
    @ApiModelProperty(value = "订单日期")
    private String              createTime;

    /** 关注的状态 */
    @Transient
    private String            attentionStatus;
    /** 异常内容 */
    @Column(name = "check_item")
    @ApiModelProperty(value = "异常内容")
    private String            checkItem;

    @ApiModelProperty(value = "大区")
    private String            region;

    @ApiModelProperty(value = "子公司")
    private String            branch;

    @ApiModelProperty(value = "事业部")
    private String            businessunit;

    @ApiModelProperty(value = "部门")
    private String            sector;

    @ApiModelProperty(value = "产品组")
    private String            productgroup;

    @ApiModelProperty(value = "成本中心")
    private String            costcenter;
    /** 存档次数 */
    @Column(name = "file_number")
    @ApiModelProperty(value = "存档次数")
    private String            fileNumber;

    /** 相关订单号 */
    @Column(name = "fei_case")
    @ApiModelProperty(value = "相关订单号")
    private String            feiCase;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetVal() {
        return targetVal;
    }

    public void setTargetVal(String targetVal) {
        this.targetVal = targetVal;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBusinessunit() {
        return businessunit;
    }

    public void setBusinessunit(String businessunit) {
        this.businessunit = businessunit;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getProductgroup() {
        return productgroup;
    }

    public void setProductgroup(String productgroup) {
        this.productgroup = productgroup;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getFeiCase() {
        return feiCase;
    }

    public void setFeiCase(String feiCase) {
        this.feiCase = feiCase;
    }

    public String getCostcenter() {
        return costcenter;
    }

    public void setCostcenter(String costcenter) {
        this.costcenter = costcenter;
    }

}
