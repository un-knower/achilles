package com.quancheng.achilles.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.model.QuanlityCheckRecord;

public interface FlyCheckRecordRepository extends JpaRepository<QuanlityCheckRecord, Long>, JpaSpecificationExecutor<QuanlityCheckRecord> {

}
