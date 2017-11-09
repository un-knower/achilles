package com.quancheng.achilles.dao.ds_st.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.ds_st.model.DataItemDetail;
public interface DataItemDetailRepository extends JpaRepository<DataItemDetail, Long>, JpaSpecificationExecutor<DataItemDetail> {
}
