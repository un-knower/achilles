package com.quancheng.achilles.dao.ds_qc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.ds_qc.model.InputRestaurantAccountInfo;
public interface RestAccountInfoRepository extends JpaRepository<InputRestaurantAccountInfo, String>, JpaSpecificationExecutor<InputRestaurantAccountInfo> {
}
