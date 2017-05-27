package com.quancheng.achilles.dao.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.quancheng.achilles.dao.model.RestaurantGonghai;

public interface RestaurantGHRepository extends JpaRepository<RestaurantGonghai, Long>,JpaSpecificationExecutor<RestaurantGonghai> {
	@Query("select t from RestaurantGonghai t ")
	Page<RestaurantGonghai> findAllWithinTimeRange( Pageable o);
	
   @Query("select distinct(t.cityDisplay) from RestaurantGonghai t where t.cityDisplay is not null and t.cityDisplay != '' ")
    List<String> listAllCity();
}
