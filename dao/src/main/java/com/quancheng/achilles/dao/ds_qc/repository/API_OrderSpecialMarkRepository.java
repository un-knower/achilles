package com.quancheng.achilles.dao.ds_qc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.ds_qc.model.API_OrderSpecialMark;
public interface API_OrderSpecialMarkRepository extends JpaRepository<API_OrderSpecialMark, String>, JpaSpecificationExecutor<API_OrderSpecialMark> {
}
