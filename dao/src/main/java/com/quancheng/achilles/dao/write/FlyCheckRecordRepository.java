package com.quancheng.achilles.dao.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.modelwrite.QuanlityCheckRecord;

public interface FlyCheckRecordRepository extends JpaRepository<QuanlityCheckRecord, Long>, JpaSpecificationExecutor<QuanlityCheckRecord> {

}
