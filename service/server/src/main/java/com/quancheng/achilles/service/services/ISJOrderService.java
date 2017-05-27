package com.quancheng.achilles.service.services;

import java.util.List;

public interface ISJOrderService {
	public void userCost(String date,Integer job) throws Exception;
	
	public <T> List<T> query(String date,Integer job,String clientId) throws Exception;
}
