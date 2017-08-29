package com.quancheng.achilles.dao.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.modelwrite.DorisTableInfo;

public interface DorisTableInfoRepository extends JpaRepository<DorisTableInfo, Long>, JpaSpecificationExecutor<DorisTableInfo> {
}
