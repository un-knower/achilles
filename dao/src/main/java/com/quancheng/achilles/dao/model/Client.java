package com.quancheng.achilles.dao.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by XZW on 2016/9/10 0010.
 */
//@Entity
//@Table(name = "16860_client")
public class Client implements Serializable {
    private static final long serialVersionUID = 8660340502323460340L;

    @Id
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "企业标识")
    private String name;

    @ApiModelProperty(value = "企业名称")
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
