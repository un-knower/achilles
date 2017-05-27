package com.quancheng.achilles.dao.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.annotations.ApiModelProperty;

/**
 * 所属企业
 * 
 * @author zhuzhong
 */
@Entity
@Table(name = "v_inn_company_own_rest")
public class RestaurantOwnCompany implements Serializable {

    private static final long serialVersionUID = -3801799321665711586L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String            id;
 
    @Column(name = "olid")
    @ApiModelProperty(value = "餐厅ID")
    private Long    olRestaurantId;
 
    @Column(name = "own_companys_id")
    @ApiModelProperty(value = "所属企业ID")
    private String  ownCompanysId;

    @Column(name = "own_companys")
    @ApiModelProperty(value = "所属企业")
    private String  ownCompanys;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getOlRestaurantId() {
		return olRestaurantId;
	}

	public void setOlRestaurantId(Long olRestaurantId) {
		this.olRestaurantId = olRestaurantId;
	}

	public String getOwnCompanysId() {
		return ownCompanysId;
	}

	public void setOwnCompanysId(String ownCompanysId) {
		this.ownCompanysId = ownCompanysId;
	}

	public String getOwnCompanys() {
		return ownCompanys;
	}

	public void setOwnCompanys(String ownCompanys) {
		this.ownCompanys = ownCompanys;
	}
    
     
}
