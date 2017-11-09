package com.quancheng.achilles.service.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quancheng.achilles.dao.ds_st.model.AchillesDiyTemplate;
import com.quancheng.achilles.dao.ds_st.model.AchillesDiyTemplateColumns;
import com.quancheng.achilles.dao.ds_st.model.AchillesTableInfo;
import com.quancheng.achilles.dao.ds_st.model.AchillesTemplateCondfig;

public interface AchillesDiyColumnsService {
    /**
     * 根据表名获取表结构信息
     * @param owner
     * @return
     */
    List<AchillesTableInfo> getTargetTableInfos(String tableName) throws Exception ;
    
    /**
     * 根据表名图表字段
     * @param owner
     * @return
     */
    List<AchillesTableInfo> getTargetTableInfos(Long tableId) throws Exception ;
    
    /**
     * 根据模版登陆用户获取模版
     * @param owner
     * @return
     */
     Page<AchillesDiyTemplate> getOwnTemplate(String tableId,String owner,Pageable pageable) ;
    /**
     * 根据模版id获取自定义字段
     * @param templateId
     * @return
     */
    List<AchillesDiyTemplateColumns> getTemplateColsByTemplate(Long templateId);
    /**
     * 
     * @param id
     * @return
     */
    AchillesDiyTemplate getTemplate(Long id);
    /**
     * 
     * @param tableName
     * @param user
     * @param templateId
     * @param columnName
     * @param templateName
     * @throws Exception
     */
    void save(Long tableId, String user,Long templateId, String[] columnName, String templateName)  throws Exception ;
    /**
     * 
     * @return
     */
    Map<String,AchillesTemplateCondfig> getTemplateConfig() ;
}
