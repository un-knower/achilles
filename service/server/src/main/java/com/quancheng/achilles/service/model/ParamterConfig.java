package com.quancheng.achilles.service.model;

import java.util.List;

public class ParamterConfig {
    private String type, colName, title;
    private List<DataItemKV> items;
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

    public ParamterConfig(String type, String colName, String title,List<DataItemKV> items) {
        super();
        this.type = type;
        this.colName = colName;
        this.title = title;
        this.items = items;
    }

    public ParamterConfig() {
        super();
    }

    public List<DataItemKV> getItems() {
        return items;
    }

    public void setItems(List<DataItemKV> items) {
        this.items = items;
    }
}
