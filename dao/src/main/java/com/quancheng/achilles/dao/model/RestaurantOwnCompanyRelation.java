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
@Table(name = "16860_restaurant_client")
public class RestaurantOwnCompanyRelation implements Serializable {

    private static final long serialVersionUID = -3801799321665711586L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String            id;
 
    @Column(name = "restaurant_id")
    @ApiModelProperty(value = "餐厅ID")
    private Long    olRestaurantId;
 
    @Column(name = "client_id")
    @ApiModelProperty(value = "所属企业ID")
    private String  clientId;

    @Column(name = "deleted_at")
    @ApiModelProperty(value = "删除时间")
    private String  deleteAt;
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
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getDeleteAt() {
		return deleteAt;
	}
	public void setDeleteAt(String deleteAt) {
		this.deleteAt = deleteAt;
	}
}
