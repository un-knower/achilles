package com.quancheng.achilles.dao.ds_st.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.annotations.ApiModelProperty;
@Entity
@Table(name = "doris_table_info")
public class DorisTableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="主键") @Column(name = "id") private Long id;
    @ApiModelProperty(value="图表名称") @Column(name = "chart_name") private String chartName;
    @ApiModelProperty(value="图表类型") @Column(name = "chart_type") private String chartType;
    @ApiModelProperty(value="图表标题") @Column(name = "chart_title") private String chartTitle;
    @ApiModelProperty(value="定义sql") @Column(name = "chart_sql") private String chartSql;
    @ApiModelProperty(value="备注") @Column(name = "remark") private String remark;
    @ApiModelProperty(value="维度字段") @Column(name = "latitude_cols") private String latitudeCols;
    @ApiModelProperty(value="值字段") @Column(name = "valus_cols") private String valusCols;
    @ApiModelProperty(value="展示字段")@Column(name = "show_cols") private String showCols;
    @Column(name = "data_source") private String dataSource;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getChartName() {
        return chartName;
    }
    public void setChartName(String chartName) {
        this.chartName = chartName;
    }
    public String getChartType() {
        return chartType;
    }
    public void setChartType(String chartType) {
        this.chartType = chartType;
    }
    public String getChartTitle() {
        return chartTitle;
    }
    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }
    public String getChartSql() {
        return chartSql;
    }
    public void setChartSql(String chartSql) {
        this.chartSql = chartSql;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getLatitudeCols() {
        return latitudeCols;
    }
    public void setLatitudeCols(String latitudeCols) {
        this.latitudeCols = latitudeCols;
    }
    public String getValusCols() {
        return valusCols;
    }
    public void setValusCols(String valusCols) {
        this.valusCols = valusCols;
    }
    public String getShowCols() {
        return showCols;
    }
    public void setShowCols(String showCols) {
        this.showCols = showCols;
    }
    public String getDataSource() {
        return dataSource;
    }
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}   
