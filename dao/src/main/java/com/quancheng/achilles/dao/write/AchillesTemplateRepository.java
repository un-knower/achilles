package com.quancheng.achilles.dao.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplate;

public interface AchillesTemplateRepository extends JpaRepository<AchillesDiyTemplate, Long>, JpaSpecificationExecutor<AchillesDiyTemplate> {
    
}
