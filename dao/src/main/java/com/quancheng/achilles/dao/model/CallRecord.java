package com.quancheng.achilles.dao.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "16860_call_record")
public class CallRecord implements Serializable {
	/**
	 * 只读，不需要生成Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8980838762360820172L;
	/** 通话日期 */
	@Column(name="record_time")
	@ApiModelProperty(value = "通话日期")
	private String recordTime;
	/** 来电人姓名 */
	@Column(name="user_name")
	@ApiModelProperty(value = "来电人姓名")
	private String userName;
	/** 来电人工号 */
	@Column(name="job_num")
	@ApiModelProperty(value = "来电人工号")
	private String jobNum;
	/** 来电号码 */
	@ApiModelProperty(value = "来电号码")
	private String telephone;
	/** 来电城市 */
	@ApiModelProperty(value = "来电城市")
	private String city;
	/** 公司 */
	@Column(name="company")
	@ApiModelProperty(value = "公司")
	private String company;
	/** 受理客服 */
	@Column(name="kefu_name")
	@ApiModelProperty(value = "受理客服")
	private String kefuName;
	/** 通话类型 */
	@ApiModelProperty(value = "通话类型(来电/去电)")
	private String type;
	/** 通话事宜 */
	@ApiModelProperty(value = "通话事宜")
	private String issue;
	/** 详细说明 */
	@ApiModelProperty(value = "详细说明")
	private String description;
	/** 解决方案 */
	@ApiModelProperty(value = "解决方案")
	private String solution;
	/** 处理结果 */
	@ApiModelProperty(value = "处理结果")
	private String result;
	/** 是否产生投诉 */
	@ApiModelProperty(value = "是否产生投诉")
	private String complain;
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
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
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getComplain() {
		return complain;
	}
	public void setComplain(String complain) {
		this.complain = complain;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getKefuName() {
		return kefuName;
	}
	public void setKefuName(String kefuName) {
		this.kefuName = kefuName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	
}
