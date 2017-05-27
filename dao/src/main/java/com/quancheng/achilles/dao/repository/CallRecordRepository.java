package com.quancheng.achilles.dao.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quancheng.achilles.dao.model.CallRecord;

public interface CallRecordRepository extends JpaRepository<CallRecord, Long>, JpaSpecificationExecutor<CallRecord> {
	@Query("select t from CallRecord t where t.recordTime>= :startTime and t.recordTime <= :endTime")
	Page<CallRecord> findAllWithinTimeRange(@Param("startTime") long startTime, @Param("endTime") long endTime, Pageable pageable);
	
	@Query("select distinct(t.company) from CallRecord t where t.company is not null and t.company != ''")
	List<String> listAllCompany();
	
	@Query("select distinct(t.type) from CallRecord t where t.type is not null and t.type != ''")
	List<String> listAllType();
	
	@Query("select distinct(t.kefuName) from CallRecord t where t.kefuName is not null and t.kefuName != ''")
	List<String> listAllKefuName();
}
