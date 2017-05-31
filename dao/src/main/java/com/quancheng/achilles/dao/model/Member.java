package com.quancheng.achilles.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.quancheng.achilles.util.DateUtils;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by XZW on 2016/9/7 0007.
 */
@Entity
@Table(name = "tmp_member")
public class Member implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1237607648018731826L;
    public static final int STATUS_DISABLE          = 0;  // 禁用
    public static final int STATUS_ENABLE           = 1;  // 正常
    public static final int STATUS_UNACTIVATED      = 2;  // 未激活
    public static final int STATUS_UNAUTHENTICATION = 3;  // 未认证
    public static final int STATUS_DELETED          = -1; // 删除
    @Id
    @ApiModelProperty(value = "ID")
    private Long            id;

    @com.ebay.xcelite.annotations.Column(name = "用户姓名")
    @ApiModelProperty(value = "用户姓名")
    private String          realname;

    @com.ebay.xcelite.annotations.Column(name = "工号")
    @Column(name = "job_num")
    @ApiModelProperty(value = "工号")
    private String          jobNum;

    @com.ebay.xcelite.annotations.Column(name = "用户名")
    @Column(name = "username")
    @ApiModelProperty(value = "用户名")
    private String          username;

    // @ApiModelProperty(value = "密码")
    // private String password;

    @com.ebay.xcelite.annotations.Column(name = "邮箱")
    @ApiModelProperty(value = "邮箱")
    private String          email;

    @com.ebay.xcelite.annotations.Column(name = "手机号")
    @ApiModelProperty(value = "手机号")
    private String          mobile;

    @com.ebay.xcelite.annotations.Column(name = "所属企业")
    @ApiModelProperty(value = "所属企业")
    @Column(name = "client_title")
    private String          clientTitle;

    @com.ebay.xcelite.annotations.Column(name = "企业标识")
    @ApiModelProperty(value = "企业标识")
    @Column(name = "client_name")
    private String          clientName;

    @com.ebay.xcelite.annotations.Column(name = "城市")
    @ApiModelProperty(value = "城市")
    private String          city;

    @com.ebay.xcelite.annotations.Column(name = "城市标识")
    @ApiModelProperty(value = "城市标识")
    @Column(name = "city_id")
    private String          cityId;

    @com.ebay.xcelite.annotations.Column(name = "上级姓名")
    @Column(name = "superior_name")
    @ApiModelProperty(value = "上级姓名")
    private String          superiorName;

    @com.ebay.xcelite.annotations.Column(name = "上级邮箱")
    @Column(name = "superior_email")
    @ApiModelProperty(value = "上级邮箱")
    private String          superiorEmail;

    @com.ebay.xcelite.annotations.Column(name = "注册时间")
    @Column(name = "reg_time")
    @ApiModelProperty(value = "注册时间")
    private String          regTime;

    @com.ebay.xcelite.annotations.Column(name = "子公司")
    @ApiModelProperty(value = "子公司")
    private String          branch;

    @com.ebay.xcelite.annotations.Column(name = "事业部")
    @ApiModelProperty(value = "事业部")
    private String          businessunit;

    @com.ebay.xcelite.annotations.Column(name = "部门")
    @ApiModelProperty(value = "部门")
    private String          sector;

    @com.ebay.xcelite.annotations.Column(name = "大区")
    @ApiModelProperty(value = "大区")
    private String          region;

    @com.ebay.xcelite.annotations.Column(name = "产品组")
    @ApiModelProperty(value = "产品组")
    private String          productgroup;

    @com.ebay.xcelite.annotations.Column(name = "成本中心")
    @ApiModelProperty(value = "成本中心")
    private String          costcenter;

    @com.ebay.xcelite.annotations.Column(name = "用户状态ID")
    @ApiModelProperty(value = "用户状态ID")
    private Integer         status;
    
    @Transient
    @com.ebay.xcelite.annotations.Column(name = "用户状态")
    @ApiModelProperty(value = "用户状态")
    private String         statusText;

    @com.ebay.xcelite.annotations.Column(name = "注册天数")
    @ApiModelProperty(value = "注册天数")
    @Column(name = "reg_days")
    private Integer         regDays;

    @com.ebay.xcelite.annotations.Column(name = "订单总数")
    @ApiModelProperty(value = "订单总数")
    @Column(name = "order_count")
    private Integer         orderCount;

    @com.ebay.xcelite.annotations.Column(name = "消费总额")
    @ApiModelProperty(value = "消费总额")
    @Column(name = "order_amount")
    private Float           orderAmount;

    @com.ebay.xcelite.annotations.Column(name = "人均偏差")
    @ApiModelProperty(value = "人均偏差")
    @Column(name = "per_mean_diff")
    private Float           perMeanDiff;

    @com.ebay.xcelite.annotations.Column(name = "提前预约指数")
    @ApiModelProperty(value = "提前预约指数")
    @Column(name = "pre_yuyue_index")
    private Float           preYuyueIndex;

    @com.ebay.xcelite.annotations.Column(name = "飞检异常次数")
    @ApiModelProperty(value = "飞检异常次数")
    @Column(name = "case_times")
    private Integer         caseTimes;

    @com.ebay.xcelite.annotations.Column(name = "消费集中度")
    @Transient
    @ApiModelProperty(value = "消费集中度")
    private Float           consumeMedian;

    @ApiModelProperty(value = "过去90天消费金额最高的餐厅中的累积消费")
    @Column(name = "max_in_3_month")
    private Float           maxExpense;

    @ApiModelProperty(value = "过去90天的总消费金额")
    @Column(name = "total_in_3_month")
    private Float           totalExpense;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getClientTitle() {
        return clientTitle;
    }

    public void setClientTitle(String clientTitle) {
        this.clientTitle = clientTitle;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSuperiorName() {
        return superiorName;
    }

    public void setSuperiorName(String superiorName) {
        this.superiorName = superiorName;
    }

    public String getSuperiorEmail() {
        return superiorEmail;
    }

    public void setSuperiorEmail(String superiorEmail) {
        this.superiorEmail = superiorEmail;
    }

    public String getRegTime() {
        return regTime==null||regTime.isEmpty()?null:DateUtils.format(regTime, DateUtils.SDF_DATE);
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Integer getRegDays() {
        return regDays > 0 ? regDays : 1;
    }

    public void setRegDays(Integer regDays) {
        this.regDays = regDays;
    }

    public Integer getOrderCount() {
        return orderCount == null ? 0 : orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Float getOrderAmount() {
        return orderAmount == null ? 0.0f : orderAmount;
    }

    public void setOrderAmount(Float orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Float getPerMeanDiff() {
        if (perMeanDiff == null) {
            perMeanDiff = 0.0f;
        }
        return (float) (Math.round(perMeanDiff * 100) / 100);
    }

    public void setPerMeanDiff(Float perMeanDiff) {
        this.perMeanDiff = perMeanDiff;
    }

    public Float getPreYuyueIndex() {
        return preYuyueIndex;
    }

    public void setPreYuyueIndex(Float preYuyueIndex) {
        this.preYuyueIndex = preYuyueIndex;
    }

    public Integer getCaseTimes() {
        return caseTimes;
    }

    public void setCaseTimes(Integer caseTimes) {
        this.caseTimes = caseTimes;
    }

    public Float getActFrequency() {
        return (float) (Math.round(orderCount * 30 / getRegDays() * 100) / 100);
    }

    public Float getPerMonth() {
        return (float) (Math.round(getOrderAmount() * 30 / getRegDays() * 100) / 100);
    }

    public Float getConsumeMedian() {
        // 计算消费集中度
        if (this.totalExpense == null || this.totalExpense == 0) {
            return 0f;
        }
        return this.maxExpense / this.totalExpense;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Float getMaxExpense() {
        return maxExpense;
    }

    public void setMaxExpense(Float maxExpense) {
        this.maxExpense = maxExpense;
    }

    public Float getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Float totalExpense) {
        this.totalExpense = totalExpense;
    }

    public void setConsumeMedian(Float consumeMedian) {
        this.consumeMedian = consumeMedian;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProductgroup() {
        return productgroup;
    }

    public void setProductgroup(String productgroup) {
        this.productgroup = productgroup;
    }

    public String getCostcenter() {
        return costcenter;
    }

    public void setCostcenter(String costcenter) {
        this.costcenter = costcenter;
    }

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

    @Override
    public String toString() {
        return "Member [id=" + id + ", realname=" + realname + ", jobNum=" + jobNum + ", username=" + username
                + ", email=" + email + ", mobile=" + mobile + ", clientTitle=" + clientTitle + ", clientName="
                + clientName + ", city=" + city + ", cityId=" + cityId + ", superiorName=" + superiorName
                + ", superiorEmail=" + superiorEmail + ", regTime=" + regTime + ", branch=" + branch + ", businessunit="
                + businessunit + ", sector=" + sector + ", region=" + region + ", productgroup=" + productgroup
                + ", costcenter=" + costcenter + ", status=" + status + ", statusText=" + statusText + ", regDays="
                + regDays + ", orderCount=" + orderCount + ", orderAmount=" + orderAmount + ", perMeanDiff="
                + perMeanDiff + ", preYuyueIndex=" + preYuyueIndex + ", caseTimes=" + caseTimes + ", consumeMedian="
                + consumeMedian + ", maxExpense=" + maxExpense + ", totalExpense=" + totalExpense + "]";
    }
}
