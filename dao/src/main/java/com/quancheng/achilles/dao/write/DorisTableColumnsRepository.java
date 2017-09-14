package com.quancheng.achilles.dao.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.modelwrite.DorisTableColumns;
public interface DorisTableColumnsRepository extends JpaRepository<DorisTableColumns, Long>, JpaSpecificationExecutor<DorisTableColumns> {
}
