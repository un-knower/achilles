package com.quancheng.achilles.service.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quancheng.achilles.dao.model.CallRecord;

public interface CallRecordService {
	
	Page<CallRecord> findAll(Date startDate, Date endDate, String company, String type, String kefuName, Pageable pageable);
	
	List<String> listAllCompany();
	
	List<String> listAllType();
	
	List<String> listAllKefuName();

}
