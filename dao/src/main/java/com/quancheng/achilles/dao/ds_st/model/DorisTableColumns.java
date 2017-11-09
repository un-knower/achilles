package com.quancheng.achilles.dao.ds_st.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "doris_table_columns")
public class DorisTableColumns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "table_id")
    private String tableId;
    
    @Column(name = "col_name")
    private String colName;
    
    @Column(name = "col_excel")
    private String colExcel;
    
    @Column(name = "show_name")
    private String showName;
    
    @Column(name = "data_type")
    private String dataType;

    @Column(name = "extra")
    private String extra;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColExcel() {
        return colExcel;
    }

    public void setColExcel(String colExcel) {
        this.colExcel = colExcel;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
    
}
