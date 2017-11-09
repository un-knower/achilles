package com.quancheng.achilles.service.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quancheng.achilles.dao.ds_qc.repository.OrderQueryRepository;
import com.quancheng.achilles.dao.ds_st.model.AchillesDiyTemplate;
import com.quancheng.achilles.dao.ds_st.model.AchillesDiyTemplateColumns;
import com.quancheng.achilles.dao.ds_st.model.AchillesTableInfo;
import com.quancheng.achilles.dao.ds_st.model.DataItem;
import com.quancheng.achilles.dao.ds_st.model.DorisTableColumns;
import com.quancheng.achilles.dao.ds_st.model.DorisTableInfo;
import com.quancheng.achilles.dao.ds_st.model.DorisTableParam;
import com.quancheng.achilles.dao.ds_st.repository.AchillesDiyTemplateColumnsRepository;
import com.quancheng.achilles.dao.ds_st.repository.AchillesTableInfoRepository;
import com.quancheng.achilles.dao.ds_st.repository.AchillesTemplateRepository;
import com.quancheng.achilles.dao.ds_st.repository.DorisTableColumnsRepository;
import com.quancheng.achilles.dao.ds_st.repository.DorisTableInfoRepository;
import com.quancheng.achilles.dao.ds_st.repository.DorisTableParamRepository;
import com.quancheng.achilles.service.model.ChartDataResp;
import com.quancheng.achilles.service.model.DorisTableTO;
import com.quancheng.achilles.service.model.PageInfo;
import com.quancheng.achilles.service.model.ParamterConfig;
@Service
public class DorisTableServiceImpl {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    DorisTableParamRepository dorisTableParamRepository;
    @Autowired
    DorisTableInfoRepository dorisTableInfoRepository;
    @Autowired
    DorisDataServiceImpl dorisDataServiceImpl;
    @Autowired
    OrderQueryRepository orderQueryRepository;
    @Autowired
    DorisTableColumnsRepository dorisTableColumnsRepository;
    @Autowired
    DataItemServiceImpl dataItemServiceImpl;
    @Autowired
    AchillesDiyTemplateColumnsRepository achillesDiyTemplateColumnsRepository;
    @Autowired
    AchillesTemplateRepository achillesTemplateRepository;
    @Autowired
    AchillesTableInfoRepository achillesTableInfoRepository;
    public List<DorisTableInfo> query(){
        return dorisTableInfoRepository.findAll();
    }
    
    
    public List<DorisTableInfo> queryDataImport(){
        return dorisTableInfoRepository.findAll(new Specification<DorisTableInfo>() {
            public Predicate toPredicate(Root<DorisTableInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("remark"),"%dtmp_%");
            }
        });
    }
    
    
    public DorisTableTO query(Long templateId){
        DorisTableTO tableInfo = new DorisTableTO();
        AchillesDiyTemplate adt =achillesTemplateRepository.findOne(templateId); 
        tableInfo.setTemplate(adt);
        tableInfo.setParams(dorisTableParamRepository.findAll(new Specification<DorisTableParam>() {
            public Predicate toPredicate(Root<DorisTableParam> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("tableId"),adt.getTemplateConfigId());
            }
        },new Sort("orderSort")));
        return tableInfo;
    }
    public DorisTableInfo dorisTableInfo(Long templateId){
       return  dorisTableInfoRepository.findOne(templateId);
    }
    public ChartDataResp getData(
                Map<String,String[]> params ,
                List<AchillesDiyTemplateColumns> columns,
                AchillesDiyTemplate template,
                PageInfo pi,
                DorisTableInfo dti ) throws Exception{
       
        String sql = dti.getChartSql();
        sql = dorisDataServiceImpl.handleSqlParam(sql, params).trim();
        Map<String,Object> par = new HashMap<>();
        par.put("sql", sql);
        List<String> cols = new ArrayList<>(columns.size());
        for (AchillesDiyTemplateColumns achillesDiyTemplateColumns : columns) {
            cols.add(achillesDiyTemplateColumns.getTableCol());
        }
        par.put("cols", cols);
        Long ps = pi.getPageSize();
        Long pn = pi.getNumber();
        par.put("pageSize", ps);
        par.put("pageBegin",  ps*pn );
        ChartDataResp cdr = new ChartDataResp();
        try {
            Long begin = System.currentTimeMillis();
            par.put("countCol", dti.getLatitudeCols());
            par.put("tableName", dti.getRemark());
            List<Map<String,Object>> result =orderQueryRepository.queryPageDataLimit(par);
            Long end = System.currentTimeMillis();
            Long count = orderQueryRepository.queryPageDataCount(par);
            Long end2 = System.currentTimeMillis();
            LOG.info("query data cost {} seconds",(end-begin)/1000);
            LOG.info("query count cost {} seconds",(end2-end)/1000);
            cdr.setDataList(result);
            cdr.setPageInfo(new PageInfo( pn,  ps, count, count% ps!=0? (count/ps)+1:count/ ps ));
        } catch (Exception e) {
            LOG.error("",e);
            throw new Exception("字段异常（可能源表某个字段被已经被删除）,请重新编辑模版");
        }
        LOG.info("query data sql: {}",sql);
        return cdr ;
    }
    
    public List<ParamterConfig> configParamater(List<DorisTableParam> params){
        List<ParamterConfig> configs = new ArrayList<>(params == null ? 1 : params.size());
        DataItem di = null;
        for (int i = 0; params != null && i < params.size(); i++) {
            DorisTableParam dtp = params.get(i);
            ParamterConfig pc = new ParamterConfig();
            pc.setTitle(dtp.getParamName());
            pc.setType(dtp.getControlType());
            pc.setColName(dtp.getParamKey());
            if (dtp.getDataItemId() != null && !dtp.getDataItemId().isEmpty()) {
                if ("single".equals(dtp.getControlType())||"multiple".equals(dtp.getControlType())) {
                    di = dataItemServiceImpl.getDataItem(dtp.getDataItemId());
                    if (di != null && "DETAIL_KV".equals(di.getItemType())) {
                        pc.setItems(dataItemServiceImpl.getDataItemDetail(di.getId()));
                    }else if (di != null && "VIEW".equals(di.getItemType())){
                        //视图
                        Map<String,Object> map = new HashMap<>();
                        map.put("viewName", di.getItemContent());
                        pc.setItems(dataItemServiceImpl.getDataItemDetail(map));
                    }
                }
            }
            configs.add(pc);
        }
        return configs;
    }
    
    public List<DorisTableColumns> getTableColumns(Long dorisTableId){
        List<DorisTableColumns> cols =  dorisTableColumnsRepository.findAll(new Specification<DorisTableColumns>() {
            public Predicate toPredicate(Root<DorisTableColumns> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("tableId"),dorisTableId);
            }
        });
        //如果没有初始化、初始化表数据
        if(cols.isEmpty()){
            DorisTableInfo table  = dorisTableInfoRepository.findOne(dorisTableId);
            if(table.getRemark().indexOf("\\.")!=-1){
                String[] arr = table.getRemark().split("\\.");
                List<AchillesTableInfo> columns = achillesTableInfoRepository.queryByTableName(arr[1], arr[0]);
                DorisTableColumns dtc = null;
                for (AchillesTableInfo achillesTableInfo : columns) {
                    if("auto_increment".equals(achillesTableInfo.getDataType())||"deleted_at".equals(achillesTableInfo.getColumnName())){
                        continue;
                    }
                    dtc = new DorisTableColumns();
                    dtc.setColExcel(achillesTableInfo.getColumnName());
                    dtc.setColName(achillesTableInfo.getColumnName());
                    dtc.setShowName(achillesTableInfo.getColumnComment()==null||achillesTableInfo.getColumnComment().isEmpty()?achillesTableInfo.getColumnName():achillesTableInfo.getColumnComment());
                    dtc.setTableId(dorisTableId.toString());
                    cols.add(dtc);
                }
                dorisTableColumnsRepository.save(cols);
            }
        }
        return cols;
    }
    
    public void modifyColumn(Long tableId,Map<String,String[]> paramaters){
        List<DorisTableColumns>  cols = this.getTableColumns(tableId);
        for (DorisTableColumns dorisTableColumns : cols) {
            String[] dataValues = paramaters.get("mapping_"+dorisTableColumns.getColName());
            String[] defaultValues = paramaters.get("default_"+dorisTableColumns.getColName());
            if(dataValues!=null && dataValues.length!=0){
                dorisTableColumns.setDataType("CONVERT_"+dataValues[0]);
            }else if(defaultValues!=null && defaultValues.length!=0){
                dorisTableColumns.setDataType("DEFAULT_"+defaultValues[0]);
            }else if(!"datetime".equals(dorisTableColumns.getDataType())){
                dorisTableColumns.setDataType(null);
            }
        }
        dorisTableColumnsRepository.save(cols);
    }
}