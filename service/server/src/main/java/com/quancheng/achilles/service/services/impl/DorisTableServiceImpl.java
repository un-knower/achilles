package com.quancheng.achilles.service.services.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quancheng.achilles.dao.modelwrite.DorisTableInfo;
import com.quancheng.achilles.dao.modelwrite.DorisTableParam;
import com.quancheng.achilles.dao.write.DorisTableInfoRepository;
import com.quancheng.achilles.dao.write.DorisTableParamRepository;
import com.quancheng.achilles.service.model.DorisTableTO;
@Service
public class DorisTableServiceImpl {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    DorisTableParamRepository dorisTableParamRepository;
    @Autowired
    DorisTableInfoRepository dorisTableInfoRepository;
    @Autowired
    DorisDataServiceImpl dorisDataServiceImpl;
    public DorisTableTO query(Long tableId){
        LOG.info("query doris info");
        DorisTableTO tableInfo = new DorisTableTO();
        DorisTableInfo table = dorisTableInfoRepository.findOne(tableId);
        List<DorisTableParam> params = dorisTableParamRepository.findAll(new Specification<DorisTableParam>() {
            public Predicate toPredicate(Root<DorisTableParam> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("chartInfoId"),tableId);
            }
        });
        tableInfo.setTable(table);
        tableInfo.setParams(params);
        return tableInfo;
    }
    
    public List<Map<String,String>> getData(Map<String,Object[]> params ,Long tableId){
        List<Map<String,String>> result = null;
        DorisTableTO dtt = query(tableId);
        DorisTableInfo dti = dtt.getTable();
        String sql = dti.getChartSql();
        sql = dorisDataServiceImpl.handelIf(sql, params);
        sql = dorisDataServiceImpl.handleSqlParam(sql, params);
        return result ;
    }
}
