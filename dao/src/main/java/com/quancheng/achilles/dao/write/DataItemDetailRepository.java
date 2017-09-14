package com.quancheng.achilles.dao.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.modelwrite.DataItemDetail;
public interface DataItemDetailRepository extends JpaRepository<DataItemDetail, Long>, JpaSpecificationExecutor<DataItemDetail> {
}
