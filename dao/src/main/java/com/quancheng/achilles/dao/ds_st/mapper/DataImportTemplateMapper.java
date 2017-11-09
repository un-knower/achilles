package com.quancheng.achilles.dao.ds_st.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface DataImportTemplateMapper {
    public Map<String,String> queryAll(Map<String,String> map);
    
    public int doInsert(Map<String,Object> map);
    
    public void deleteAllData(@Param("tableName") String tableName);
}
