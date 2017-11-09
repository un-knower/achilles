package com.quancheng.achilles.dao.ds_qc.model;

public class SJUserCost extends SJBaseObject {
	private String dataType;
	
	private String currentValue;
	
	private Double lastMonthThan;

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public Double getLastMonthThan() {
		return lastMonthThan;
	}

	public void setLastMonthThan(Double lastMonthThan) {
		this.lastMonthThan = lastMonthThan;
	}
	
}
