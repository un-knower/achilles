package com.quancheng.achilles.dao.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.quancheng.achilles.dao.modelwrite.DorisTableParam;
public interface DorisTableParamRepository extends JpaRepository<DorisTableParam, Long>, JpaSpecificationExecutor<DorisTableParam> {
}
