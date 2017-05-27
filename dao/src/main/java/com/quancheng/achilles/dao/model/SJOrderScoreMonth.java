package com.quancheng.achilles.dao.model;

public class SJOrderScoreMonth extends SJBaseObject {
	 private String score;
	 private Integer orderCount;
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
}
