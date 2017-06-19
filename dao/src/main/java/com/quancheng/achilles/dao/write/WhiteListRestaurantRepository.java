package com.quancheng.achilles.dao.write;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.quancheng.achilles.dao.modelwrite.WhiteListRestaurant;

public interface WhiteListRestaurantRepository extends JpaRepository<WhiteListRestaurant, Long>, JpaSpecificationExecutor<WhiteListRestaurant> {
    
    @Query(value="SELECT distinct title FROM out_white_list_restaurant",nativeQuery=true)
    List<String> queryClientList();
}
