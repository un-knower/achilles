package com.quancheng.achilles.service.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ChartDataResp implements Serializable {

    private static final long         serialVersionUID = -1752904409128180604L;

    private PageInfo                  pageInfo;
    private Long                   templateId;
    private List<Map<String, Object>> dataList;
    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
  
    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public List<Map<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }
    
}
