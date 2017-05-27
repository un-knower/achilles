package com.quancheng.achilles.service.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quancheng.achilles.dao.model.RestaurantVisitLog;

public interface RestaurantVisitLogService {

	Page<RestaurantVisitLog> findAll(Date startDate, Date endDate,String restStatus ,  String type,  String[] city, String restaurantName,
	        String salesName, Pageable pageable);

	List<String> listAllType();

	List<String> listAllCityName();

	List<String> listAllSalesName();
}
