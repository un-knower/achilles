package com.quancheng.achilles.service.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quancheng.achilles.dao.model.QuanlityCheckRecord;

/**
 * @author pcuser 2016年9月21日 上午11:33:32
 * @version $Id: QuanlityCheckService.java, v 0.0.1 2016年9月21日 上午11:33:32 jianglijun Exp $
 */
public interface QuanlityCheckService {

    Page<QuanlityCheckRecord> findAll(Pageable pageable);

    // 条件select
    Page<QuanlityCheckRecord> findAll(String[] state,String checkreason, Date startDate, Date endDate, String company, String checkType, String checkMode,
                                      String[] cityName, String shangpuName, Pageable pageable);

    List<String> listAllCompany();

    List<String> listAllCheckType();

    List<String> listAllCheckMode();

    List<String> listAllCityName();

}
