package com.quancheng.achilles.dao.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.modelwrite.DataItem;
public interface DataItemRepository extends JpaRepository<DataItem, Long>, JpaSpecificationExecutor<DataItem> {
}
