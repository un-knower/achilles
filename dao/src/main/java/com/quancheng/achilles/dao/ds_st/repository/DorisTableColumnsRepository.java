package com.quancheng.achilles.dao.ds_st.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.ds_st.model.DorisTableColumns;
public interface DorisTableColumnsRepository extends JpaRepository<DorisTableColumns, Long>, JpaSpecificationExecutor<DorisTableColumns> {
}
