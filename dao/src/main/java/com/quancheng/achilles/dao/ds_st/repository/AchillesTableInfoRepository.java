package com.quancheng.achilles.dao.ds_st.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quancheng.achilles.dao.ds_st.model.AchillesTableInfo;

public interface AchillesTableInfoRepository
        extends JpaRepository<AchillesTableInfo,String> , JpaSpecificationExecutor<AchillesTableInfo> {

    @Query(nativeQuery=true,value="select  column_name  , column_comment  ,table_name  , table_schema ,data_type,extra"
            + " from information_schema.`COLUMNS` where table_Schema=:schemaName and table_Name=:tableName")
    public List<AchillesTableInfo> queryByTableName(@Param("tableName") String tableName,
            @Param("schemaName") String schemaName);
}
