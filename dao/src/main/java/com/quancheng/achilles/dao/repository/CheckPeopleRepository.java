package com.quancheng.achilles.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.model.CheckEmphasisPeople;

public interface CheckPeopleRepository extends JpaRepository<CheckEmphasisPeople, String>, JpaSpecificationExecutor<CheckEmphasisPeople> {

}
