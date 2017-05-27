package com.quancheng.achilles.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quancheng.achilles.dao.model.RestaurantOwnCompany;

public interface RestaurantOwnCompanyRepository extends JpaRepository<RestaurantOwnCompany, Long>,JpaSpecificationExecutor<RestaurantOwnCompany> {
	
	@Query("select  t from RestaurantOwnCompany t where olRestaurantId in (:restaurantids)")
	public List<RestaurantOwnCompany> findAllByRestIds(@Param("restaurantids") List<Long> restaurantids);
	
	@Query("select  t from RestaurantOwnCompany t where olRestaurantId in (:restaurantids) and ownCompanysId=:ownCompanysId")
	public List<RestaurantOwnCompany> findAllByRestIds(@Param("restaurantids") List<Long> restaurantids ,@Param("ownCompanysId")  String  companyId);
	
}
