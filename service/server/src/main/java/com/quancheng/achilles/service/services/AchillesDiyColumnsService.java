package com.quancheng.achilles.service.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplate;
import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplateColumns;
import com.quancheng.achilles.dao.modelwrite.AchillesTableInfo;

public interface AchillesDiyColumnsService {
    /**
     * 根据表名获取表结构信息
     * @param owner
     * @return
     */
    List<AchillesTableInfo> getTargetTableInfos(String tableName);
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
    
    AchillesDiyTemplate getTemplate(Long id);
    
    void save(String tableName, String user,Long templateId, String[] columnName, String templateName);
}
