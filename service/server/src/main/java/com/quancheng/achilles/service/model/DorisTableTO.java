package com.quancheng.achilles.service.model;

import java.util.List;

import com.quancheng.achilles.dao.ds_st.model.AchillesDiyTemplate;
import com.quancheng.achilles.dao.ds_st.model.AchillesDiyTemplateColumns;
import com.quancheng.achilles.dao.ds_st.model.DorisTableParam;

public class DorisTableTO {
    private AchillesDiyTemplate template;
    
    private List<DorisTableParam> params;

    private List<AchillesDiyTemplateColumns> cols;

    public AchillesDiyTemplate getTemplate() {
        return template;
    }

    public void setTemplate(AchillesDiyTemplate template) {
        this.template = template;
    }

    public List<DorisTableParam> getParams() {
        return params;
    }

    public void setParams(List<DorisTableParam> params) {
        this.params = params;
    }

    public List<AchillesDiyTemplateColumns> getCols() {
        return cols;
    }

    public void setCols(List<AchillesDiyTemplateColumns> cols) {
        this.cols = cols;
    } 
}
