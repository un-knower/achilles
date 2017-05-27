package com.quancheng.achilles.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.model.CheckEmphasisDining;

public interface CheckDiningRepository extends JpaRepository<CheckEmphasisDining, String>, JpaSpecificationExecutor<CheckEmphasisDining> {

}
