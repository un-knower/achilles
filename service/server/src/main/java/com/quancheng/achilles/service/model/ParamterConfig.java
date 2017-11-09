package com.quancheng.achilles.service.model;

import java.util.List;

import com.quancheng.achilles.dao.ds_st.model.DataItemDetail;

public class ParamterConfig {
    private String type, colName, title;
    private List<DataItemDetail> items;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ParamterConfig(String type, String colName, String title,List<DataItemDetail> items) {
        super();
        this.type = type;
        this.colName = colName;
        this.title = title;
        this.items = items;
    }

    public ParamterConfig() {
        super();
    }

    public List<DataItemDetail> getItems() {
        return items;
    }

    public void setItems(List<DataItemDetail> items) {
        this.items = items;
    }
}
