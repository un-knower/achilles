package com.quancheng.achilles.service.model;

import java.io.Serializable;
import java.util.Map;

public class ChartDataReq implements Serializable {

    private static final long   serialVersionUID = -1752904409128180604L;

    private PageInfo            pageInfo;
    private Long             tableId;
    private Map<String, Object[]> params;

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Map<String, Object[]> getParams() {
        return params;
    }

    public void setParams(Map<String, Object[]> params) {
        this.params = params;
    }

}
