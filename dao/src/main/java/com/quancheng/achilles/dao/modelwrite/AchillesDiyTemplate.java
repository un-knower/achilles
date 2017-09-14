package com.quancheng.achilles.dao.modelwrite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "achilles_diy_template")
public class AchillesDiyTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /***/
    @Column(name = "table_name")
    private String tableName;
    /** 数据库表名 */
    @Column(name = "template_name")
    private String templateName;
    /** 模版名称 */
    @Column(name = "created_user")
    private String createdUser;
    /** 模版创建人 */
    @Column(name = "data_base")
    private String dataBase;
    @Column(name = "created_at")
    private String createdAt;
    
    //数据源表id
    @Column(name = "template_config_id")
    private Long templateConfigId;
    /** 源表所在schema */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public AchillesDiyTemplate(  String tableName,  String createdUser) {
        super();
        this.tableName = tableName;
        this.createdUser = createdUser;
    }
    public AchillesDiyTemplate(  Long templateConfigId,  String createdUser) {
        super();
        this.templateConfigId = templateConfigId;
        this.createdUser = createdUser;
    }
    public AchillesDiyTemplate() {
    }

    public Long getTemplateConfigId() {
        return templateConfigId;
    }

    public void setTemplateConfigId(Long templateConfigId) {
        this.templateConfigId = templateConfigId;
    }
}
