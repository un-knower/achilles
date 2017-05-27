package com.quancheng.achilles.service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quancheng.achilles.dao.model.CheckEmphasisPeople;

public interface CheckPeopleRestService {

    Page<CheckEmphasisPeople> findAll(Pageable pageable);

}
