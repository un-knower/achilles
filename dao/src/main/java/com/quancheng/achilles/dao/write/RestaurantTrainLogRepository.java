package com.quancheng.achilles.dao.write;

//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Query;
//
//import com.quancheng.achilles.dao.modelwrite.RestaurantTrainLog;
//
//public interface RestaurantTrainLogRepository extends JpaRepository<RestaurantTrainLog, Long>, JpaSpecificationExecutor<RestaurantTrainLog> {
//	@Query("select distinct(t.cityName) from RestaurantTrainLog t")
//	List<String> listAllCityName();
//	
//	
//	@Query("select distinct(t.salesName) from RestaurantTrainLog t")
//	List<String> listAllSalesName();
//}
