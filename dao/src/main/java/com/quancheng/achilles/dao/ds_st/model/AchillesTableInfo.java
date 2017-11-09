package com.quancheng.achilles.dao.ds_st.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class AchillesTableInfo {
    @Id
    @Column(name="column_name")
    private String columnName;
    @Column(name="column_comment")
    private String columnComment;
    
    @Column(name="data_type")
    private String dataType;
    
    @Column(name="table_name")
    private String tableName;
    @Column(name="table_schema")
    private String tableSchema;
    
    
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public AchillesTableInfo(String columnName, String columnComment) {
        super();
        this.columnName = columnName;
        this.columnComment = columnComment;
    }
    public AchillesTableInfo() {
        super();
    }
}
