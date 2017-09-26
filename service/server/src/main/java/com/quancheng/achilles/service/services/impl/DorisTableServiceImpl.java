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
import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplate;
import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplateColumns;
import com.quancheng.achilles.dao.modelwrite.DataItem;
import com.quancheng.achilles.dao.modelwrite.DorisTableInfo;
import com.quancheng.achilles.dao.modelwrite.DorisTableParam;
import com.quancheng.achilles.dao.repository.OrderQueryRepository;
import com.quancheng.achilles.dao.write.AchillesDiyTemplateColumnsRepository;
import com.quancheng.achilles.dao.write.AchillesTemplateRepository;
import com.quancheng.achilles.dao.write.DorisTableColumnsRepository;
import com.quancheng.achilles.dao.write.DorisTableInfoRepository;
import com.quancheng.achilles.dao.write.DorisTableParamRepository;
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
    
    
    public List<DorisTableInfo> query(){
        return dorisTableInfoRepository.findAll();
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
}