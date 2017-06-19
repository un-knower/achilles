package com.quancheng.achilles.dao.modelwrite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "achilles_diy_template_columns")
public class AchillesDiyTemplateColumns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 模版id */
    @Column(name = "template_id")
    private Long templateId;
    /** 列 */
    @Column(name = "table_col")
    private String tableCol;
    /** 列头 */
    @Column(name = "col_titile")
    private String colTitile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTableCol() {
        return tableCol;
    }

    public void setTableCol(String tableCol) {
        this.tableCol = tableCol;
    }

    public String getColTitile() {
        return colTitile;
    }

    public void setColTitile(String colTitile) {
        this.colTitile = colTitile;
    }
}
