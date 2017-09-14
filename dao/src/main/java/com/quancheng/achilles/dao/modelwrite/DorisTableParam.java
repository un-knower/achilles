package com.quancheng.achilles.dao.modelwrite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "doris_table_param")
public class DorisTableParam {
    @ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ApiModelProperty(value = "配置表id")
    @Column(name = "chart_info_id")
    private Long tableId;
    @ApiModelProperty(value = "参数key")
    @Column(name = "param_key")
    private String paramKey;
    @ApiModelProperty(value = "参数名称")
    @Column(name = "param_name")
    private String paramName;
    @ApiModelProperty(value = "数据类型")
    @Column(name = "data_type")
    private String dataType;
    @ApiModelProperty(value = "控件类型（时间，选择，单选，隐藏）（下拉需要与数据字典关联）")
    @Column(name = "control_type")
    private String controlType;
    @ApiModelProperty(value = "选择控件数据源(字典key)")
    @Column(name = "data_item_id")
    private String dataItemId;
    @ApiModelProperty(value = "排序")
    @Column(name = "order_sort")
    private Integer orderSort;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getTableId() {
        return tableId;
    }
    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }
    public String getParamKey() {
        return paramKey;
    }
    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }
    public String getParamName() {
        return paramName;
    }
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public String getControlType() {
        return controlType;
    }
    public void setControlType(String controlType) {
        this.controlType = controlType;
    }
    public String getDataItemId() {
        return dataItemId;
    }
    public void setDataItemId(String dataItemId) {
        this.dataItemId = dataItemId;
    }
    public Integer getOrderSort() {
        return orderSort;
    }
    public void setOrderSort(Integer orderSort) {
        this.orderSort = orderSort;
    }
}
