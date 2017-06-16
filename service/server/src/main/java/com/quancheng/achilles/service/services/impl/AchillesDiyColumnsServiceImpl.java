package com.quancheng.achilles.service.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.quancheng.achilles.dao.write.AchillesDiyTemplateColumnsRepository;
import com.quancheng.achilles.dao.write.AchillesTableInfoRepository;
import com.quancheng.achilles.dao.write.AchillesTemplateRepository;
import com.quancheng.achilles.service.services.AchillesDiyColumnsService;
import com.quancheng.achilles.service.services.RestaurantServiceAbstract;
import com.quancheng.achilles.util.DateUtils;

@Service
public class AchillesDiyColumnsServiceImpl extends RestaurantServiceAbstract<AchillesDiyTemplate> implements AchillesDiyColumnsService{
    @Autowired
    AchillesTableInfoRepository achillesTableInfoRepository;
    @Autowired
    AchillesDiyTemplateColumnsRepository achillesDiyTemplateColumnsRepository;
    @Autowired
    AchillesTemplateRepository achillesTemplateRepository;

    public List<AchillesTableInfo> getTargetTableInfos(String tableName) {
        return achillesTableInfoRepository.queryByTableName(tableName, "quancheng_db");
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
    
    public Integer saveTemplate(List<AchillesDiyTemplateColumns> templateColumns ,AchillesDiyTemplate template ) {
        if(template.getId() != null ){
            achillesDiyTemplateColumnsRepository.deleteInBatch(getTemplateColsByTemplate(template.getId()));
        }else{
            template.setCreatedAt(DateUtils.format(new Date()));
        }
        achillesTemplateRepository.save(template);
        for (AchillesDiyTemplateColumns achillesDiyTemplateColumns : templateColumns) {
            achillesDiyTemplateColumns.setTemplateId(template.getId());
        }
        achillesDiyTemplateColumnsRepository.save(templateColumns);
        return 1;
    }

    public void save(String tableName, String user,Long templateId, String[] columnName, String templateName) {
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
        saveTemplate(templateColumns,adt);
    }
}
