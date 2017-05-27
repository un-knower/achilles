package com.quancheng.achilles.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.quancheng.achilles.dao.model.RestaurantVisitLog;

public interface RestaurantVisitLogRepository extends JpaRepository<RestaurantVisitLog, Long>, JpaSpecificationExecutor<RestaurantVisitLog> {
	
	@Query("select distinct(t.cityName) from RestaurantVisitLog t")
	List<String> listAllCityName();
	
	@Query("select distinct(t.type) from RestaurantVisitLog t")
	List<String> listAllType();
	
	@Query("select distinct(t.salesName) from RestaurantVisitLog t")
	List<String> listAllSalesName();

}
