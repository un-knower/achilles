package com.quancheng.achilles.dao.quancheng_db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.quancheng_db.model.SytHospitalRelation;

public interface SytHospitalRelationRepository extends JpaRepository<SytHospitalRelation, Long>, JpaSpecificationExecutor<SytHospitalRelation> {
    
    
}
