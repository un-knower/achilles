package com.quancheng.achilles.service.services;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.quancheng.achilles.dao.ds_st.model.DorisTableColumns;
import com.quancheng.achilles.dao.ds_st.model.DorisTableInfo;
import com.quancheng.achilles.service.model.ChartDataResp;

/**
 * poi / csv 文件导入
 * 
 * @author zhuzhong
 */
public interface DataImportService {
    public void doImport(File file, Long dorisTableId);
    
    public ChartDataResp dataView(Map<String,String[]> paramters, List<DorisTableColumns> cols,DorisTableInfo table);
}
