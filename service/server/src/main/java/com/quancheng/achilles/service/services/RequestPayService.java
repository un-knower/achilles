package com.quancheng.achilles.service.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quancheng.achilles.dao.modelwrite.RequestPayment;

public interface RequestPayService {

    Page<RequestPayment> findAll(Date startTime, Date endTime, String relateOrder, String[] city, Pageable pageable);

    List<String> listAllCityName();
}
