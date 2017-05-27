package com.quancheng.achilles.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.quancheng.achilles.dao.model.RestaurantRecommender;

public interface RestaurantRecommenderRepository extends   JpaRepository<RestaurantRecommender, Long>,JpaSpecificationExecutor<RestaurantRecommender> {
	@Query("select t from RestaurantRecommender t ")
	Page<RestaurantRecommender> findAll( Pageable o);
}
