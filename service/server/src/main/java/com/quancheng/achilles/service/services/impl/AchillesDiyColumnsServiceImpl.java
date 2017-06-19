package com.quancheng.achilles.service.services.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplate;
import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplateColumns;
import com.quancheng.achilles.dao.modelwrite.AchillesTableInfo;
import com.quancheng.achilles.dao.modelwrite.AchillesTemplateCondfig;
import com.quancheng.achilles.dao.write.AchillesDiyTemplateColumnsRepository;
import com.quancheng.achilles.dao.write.AchillesTableInfoRepository;
import com.quancheng.achilles.dao.write.AchillesTemplateConfigRepository;
import com.quancheng.achilles.dao.write.AchillesTemplateRepository;
import com.quancheng.achilles.service.services.AchillesDiyColumnsService;
import com.quancheng.achilles.service.services.RestaurantServiceAbstract;
import com.quancheng.achilles.util.DateUtils;

import io.swagger.annotations.ApiModelProperty;

@Service
public class AchillesDiyColumnsServiceImpl extends RestaurantServiceAbstract<AchillesDiyTemplate> implements AchillesDiyColumnsService{
    @Autowired
    AchillesTableInfoRepository achillesTableInfoRepository;
    @Autowired
    AchillesDiyTemplateColumnsRepository achillesDiyTemplateColumnsRepository;
    @Autowired
    AchillesTemplateRepository achillesTemplateRepository;
    @Autowired
    AchillesTemplateConfigRepository achillesTemplateConfigRepository;
    
    public List<AchillesTableInfo> getTargetTableInfos(String tableName) throws Exception {
        AchillesTemplateCondfig atc = getTemplateConfig(tableName);
        List<AchillesTableInfo> result = null;
        if(atc == null ){
               throw new Exception("模版配置异常，联系技术！");
        }
        if( atc.getTemplateClass() ==null || atc.getTemplateClass().isEmpty() ){
            result = achillesTableInfoRepository.queryByTableName(tableName, "quancheng_db");
        }else{
            Class<?> cls = Class.forName(atc.getTemplateClass());
            Field[] fields = cls.getDeclaredFields();
            result = new ArrayList<>(fields.length);
            AchillesTableInfo ati = null;
            ApiModelProperty amp = null;
            for (Field field : fields) {
                ati = new AchillesTableInfo();
                ati.setColumnName(field.getName());
                amp = field.getAnnotation(ApiModelProperty.class);
                ati.setColumnComment(amp == null ? ati.getColumnName():amp.value());
                result.add(ati);
            }
        }
        return result;
    }

    public Page<AchillesDiyTemplate> getOwnTemplate(String tableName,String owner,Pageable pageable) {
        Specifications<AchillesDiyTemplate>  speci= 
                Specifications.where(equal("createdUser",owner))
                .and(equal("tableName",tableName));
        return achillesTemplateRepository.findAll(speci,pageable);
    }

    public AchillesDiyTemplate getTemplate(Long id) {
        return achillesTemplateRepository.findOne(id);
    }
    
    public List<AchillesDiyTemplateColumns> getTemplateColsByTemplate(Long templateId) {
        return achillesDiyTemplateColumnsRepository.findAll( new Specification<AchillesDiyTemplateColumns>() {
            public Predicate toPredicate(Root<AchillesDiyTemplateColumns> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return  cb.equal(root.get("templateId"), templateId);
            }
        });
    }
    
    public AchillesTemplateCondfig getTemplateConfig(String tableName) {
        return achillesTemplateConfigRepository.findOne( new Specification<AchillesTemplateCondfig>() {
            public Predicate toPredicate(Root<AchillesTemplateCondfig> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return  cb.equal(root.get("tableName"), tableName);
            }
        });
    }
    
    public Map<String,AchillesTemplateCondfig> getTemplateConfig() {
        List<AchillesTemplateCondfig> list = achillesTemplateConfigRepository.findAll();
        Map<String,AchillesTemplateCondfig> map = new HashMap<>(list.size());
        for (AchillesTemplateCondfig achillesTemplateCondfig : list) {
            map.put(achillesTemplateCondfig.getTableName(), achillesTemplateCondfig);
        }
        return map;
    }
    
    
    public Integer saveTemplate(List<AchillesDiyTemplateColumns> templateColumns ,AchillesDiyTemplate template ) throws Exception {
        if(template.getId() != null ){
            achillesDiyTemplateColumnsRepository.deleteInBatch(getTemplateColsByTemplate(template.getId()));
        }else{
            AchillesTemplateCondfig atc = null;
            try {
                atc = getTemplateConfig(template.getTableName());
            } catch (Exception e) {
                throw new Exception("模版配置异常，联系技术人员！！");
            }
            if(atc == null ){
                throw new Exception("模版配置异常，联系技术人员！");
            }
            template.setTemplateConfigId(atc.getId());
            template.setCreatedAt(DateUtils.format(new Date()));
        }
        achillesTemplateRepository.save(template);
        for (AchillesDiyTemplateColumns achillesDiyTemplateColumns : templateColumns) {
            achillesDiyTemplateColumns.setTemplateId(template.getId());
        }
        achillesDiyTemplateColumnsRepository.save(templateColumns);
        return 1;
    }

    public void save(String tableName, String user,Long templateId, String[] columnName, String templateName) throws Exception {
        List<AchillesTableInfo>  cols = getTargetTableInfos(tableName);
        List<AchillesDiyTemplateColumns> templateColumns = new ArrayList<>(columnName.length);
        AchillesDiyTemplateColumns adtc = null;
        List<String> colsSelected = Arrays.asList(columnName);
        for (AchillesTableInfo achillesDiyTemplateColumns : cols) {
            if(colsSelected.contains(achillesDiyTemplateColumns.getColumnName())){
                adtc = new AchillesDiyTemplateColumns();
                adtc.setColTitile(achillesDiyTemplateColumns.getColumnComment());
                adtc.setTableCol(achillesDiyTemplateColumns.getColumnName());
                templateColumns.add(adtc);
            }
        }
        AchillesDiyTemplate adt = templateId == null ? new  AchillesDiyTemplate(tableName,user): getTemplate(templateId);
        adt.setTemplateName(templateName);
        saveTemplate(templateColumns,adt);
    }
}
