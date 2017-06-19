package com.quancheng.achilles.dao.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.modelwrite.AchillesTemplateCondfig;

public interface AchillesTemplateConfigRepository extends JpaRepository<AchillesTemplateCondfig, Long>, JpaSpecificationExecutor<AchillesTemplateCondfig> {
    
}
