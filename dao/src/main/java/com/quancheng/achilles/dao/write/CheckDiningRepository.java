package com.quancheng.achilles.dao.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.modelwrite.CheckEmphasisDining;

public interface CheckDiningRepository extends JpaRepository<CheckEmphasisDining, String>, JpaSpecificationExecutor<CheckEmphasisDining> {

}
