package com.quancheng.achilles.dao.modelwrite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "achilles_template_config")
public class AchillesTemplateCondfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /***/
    @Column(name = "table_name")
    private String tableName;
    /** 数据库表名 */
    @Column(name = "template_url")
    private String templateUrl;
    @Column(name = "created_at")
    private String createdAt;
    
    @Column(name = "template_class")
    private String templateClass;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public AchillesTemplateCondfig() {
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public String getTemplateClass() {
        return templateClass;
    }

    public void setTemplateClass(String templateClass) {
        this.templateClass = templateClass;
    }
}
