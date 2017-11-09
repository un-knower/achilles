package com.quancheng.achilles.dao.ds_qc.model;

public class SJFlyCheckMonthStastic extends SJBaseObject {
	private String city;
	
	private Integer orderCount;
	
	private Integer checkCount;
	
	private Integer ingCountCorrect;
	
	private Integer ingCountIncorrect;
	
	private Double ingCheckRate;
	
	private String ingCountReason;
	
	private Integer afterCountCorrect;
	
	private Integer afterCountIncorrect;
	private Double afterCheckRate;
	private String afterCountReason;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public Integer getCheckCount() {
		return checkCount;
	}
	public void setCheckCount(Integer checkCount) {
		this.checkCount = checkCount;
	}
	public Integer getIngCountCorrect() {
		return ingCountCorrect;
	}
	public void setIngCountCorrect(Integer ingCountCorrect) {
		this.ingCountCorrect = ingCountCorrect;
	}
	public Integer getIngCountIncorrect() {
		return ingCountIncorrect;
	}
	public void setIngCountIncorrect(Integer ingCountIncorrect) {
		this.ingCountIncorrect = ingCountIncorrect;
	}
	public Double getIngCheckRate() {
		return ingCheckRate;
	}
	public void setIngCheckRate(Double ingCheckRate) {
		this.ingCheckRate = ingCheckRate;
	}
	public String getIngCountReason() {
		return ingCountReason;
	}
	public void setIngCountReason(String ingCountReason) {
		this.ingCountReason = ingCountReason;
	}
	public Integer getAfterCountCorrect() {
		return afterCountCorrect;
	}
	public void setAfterCountCorrect(Integer afterCountCorrect) {
		this.afterCountCorrect = afterCountCorrect;
	}
	public Integer getAfterCountIncorrect() {
		return afterCountIncorrect;
	}
	public void setAfterCountIncorrect(Integer afterCountIncorrect) {
		this.afterCountIncorrect = afterCountIncorrect;
	}
	public Double getAfterCheckRate() {
		return afterCheckRate;
	}
	public void setAfterCheckRate(Double afterCheckRate) {
		this.afterCheckRate = afterCheckRate;
	}
	public String getAfterCountReason() {
		return afterCountReason;
	}
	public void setAfterCountReason(String afterCountReason) {
		this.afterCountReason = afterCountReason;
	}
}
