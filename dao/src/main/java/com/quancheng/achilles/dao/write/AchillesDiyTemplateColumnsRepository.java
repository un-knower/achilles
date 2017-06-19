package com.quancheng.achilles.dao.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplateColumns;

public interface AchillesDiyTemplateColumnsRepository extends JpaRepository<AchillesDiyTemplateColumns, Long>, JpaSpecificationExecutor<AchillesDiyTemplateColumns> {
    
    
}
