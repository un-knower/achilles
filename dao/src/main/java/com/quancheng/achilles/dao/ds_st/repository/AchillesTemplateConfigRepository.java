package com.quancheng.achilles.dao.ds_st.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.ds_st.model.AchillesTemplateCondfig;

public interface AchillesTemplateConfigRepository extends JpaRepository<AchillesTemplateCondfig, Long>, JpaSpecificationExecutor<AchillesTemplateCondfig> {
    
}
