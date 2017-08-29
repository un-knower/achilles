package com.quancheng.achilles.service.model;

import java.util.List;

import com.quancheng.achilles.dao.modelwrite.DorisTableInfo;
import com.quancheng.achilles.dao.modelwrite.DorisTableParam;

public class DorisTableTO {
    private DorisTableInfo table;
    
    private List<DorisTableParam> params;

    public DorisTableInfo getTable() {
        return table;
    }

    public void setTable(DorisTableInfo table) {
        this.table = table;
    }

    public List<DorisTableParam> getParams() {
        return params;
    }

    public void setParams(List<DorisTableParam> params) {
        this.params = params;
    } 
}
