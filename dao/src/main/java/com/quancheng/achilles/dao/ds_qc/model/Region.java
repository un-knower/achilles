package com.quancheng.achilles.dao.ds_qc.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "16860_region")
public class Region implements Serializable{
    private static final long serialVersionUID = 222341375071619688L;

    public static final Integer STATUS_ENABLE=1; //正常
    public static final Integer STATUS_DISABLE=0; //禁用

    public static final Integer LEVEL_PROVINCE=1; //省
    public static final Integer LEVEL_CITY=2; //市
    public static final Integer LEVEL_DISTRICT=3; //区

    public static final Integer TYPE_REGION=1; //行政区域
    public static final Integer TYPE_AREA=2; //商圈


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    @ApiModelProperty(value = "城市id")
    private Long id ;
    
    @Column(name="name")
    @ApiModelProperty(value = "城市或区域名")
    private String regionName;
    
    @Column(name="type")
    @ApiModelProperty(value = "区域类型 1：行政区域 2：商圈")
    private String type;
    
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRegionName() {
        return regionName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    
}
