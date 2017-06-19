package com.quancheng.achilles.service.services;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplate;
import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplateColumns;
import com.quancheng.achilles.dao.modelwrite.AchillesTableInfo;
import com.quancheng.achilles.dao.modelwrite.AchillesTemplateCondfig;

public interface AchillesDiyColumnsService {
    /**
     * 根据表名获取表结构信息
     * @param owner
     * @return
     */
    List<AchillesTableInfo> getTargetTableInfos(String tableName) throws Exception ;
    /**
     * 根据模版登陆用户获取模版
     * @param owner
     * @return
     */
     Page<AchillesDiyTemplate> getOwnTemplate(String tableName,String owner,Pageable pageable) ;
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
    void save(String tableName, String user,Long templateId, String[] columnName, String templateName)  throws Exception ;
    /**
     * 
     * @return
     */
    Map<String,AchillesTemplateCondfig> getTemplateConfig() ;
}
