package com.quancheng.achilles.service.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quancheng.achilles.dao.modelwrite.RestaurantTrainLog;

public interface RestaurantTrainLogService {

	Page<RestaurantTrainLog> findAll(Date startDate, Date endDate, String company, String[] city, String restaurantName,
	        String salesName, Pageable pageable);

	List<String>  listAllCityName();

	List<String>  listAllSalesName();

}
