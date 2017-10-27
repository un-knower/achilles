package com.quancheng.achilles.dao.quancheng_db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.quancheng_db.model.UcbArcCode;

public interface UcbArcCodeRepository extends JpaRepository<UcbArcCode, Long>, JpaSpecificationExecutor<UcbArcCode> {
    
    
}
