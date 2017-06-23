package com.quancheng.achilles.dao.modelwrite;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author lijun jiang
 * @version 创建时间：2016年9月8日上午10:05:30
 */
@Entity
@Table(name = "tmp_request_payment")
public class RequestPayment implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1857245906628417071L;
    /**
     * 只读，不需要生成Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String            id;

    /** 订单号 */
    @Column(name = "order_num")
    @ApiModelProperty(value = "订单号")
    private String            relateOrder;

    /** 订单状态 */
    @Column(name = "order_state")
    @ApiModelProperty(value = "订单状态")
    private String           orderState;

    /** 餐厅名 */
    @Column(name = "restaurant_name")
    @ApiModelProperty(value = "餐厅名")
    private String            restaurantName;

    /** 城市 */
    @Column(name = "city_id")
    private String            cityId;

    /** 城市 */
    @Column(name = "city_name")
    @ApiModelProperty(value = "城市")
    private String            cityName;
    
    /** 预约时间 */
    @Column(name = "yuyue_time")
    @ApiModelProperty(value = "预约时间")
    private String           yuyue_time;

    /** 下单人姓名 */
    @Column(name = "realname")
    @ApiModelProperty(value = "下单人姓名")
    private String            realname;

    /** 下单人工号 */
    @Column(name = "job_num")
    @ApiModelProperty(value = "下单人工号")
    private String            jobNum;

    /** 下单人手机号 */
    @Column(name = "mobile")
    @ApiModelProperty(value = "下单人手机号")
    private String            mobile;

    /** 消费金额 */
    @Column(name = "money")
    @ApiModelProperty(value = "消费金额")
    private Double            money;

    /** 消费人数 */
    @Column(name = "actual_people")
    @ApiModelProperty(value = "消费人数")
    private Integer           actualPeople;

    /** 催款日期 */
    @Column(name = "created_at")
    @ApiModelProperty(value = "催款日期")
    private Date              createdAt;

    /** 状态 */
    @Column(name = "status")
    @ApiModelProperty(value = "催款状态")
    private String           status;

    
    /** 客服 */
    @Column(name = "kefu")
    @ApiModelProperty(value = "客服")
    private String kefu;
    
    
    /** 备注 */
    @Column(name = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelateOrder() {
        return relateOrder;
    }

    public void setRelateOrder(String relateOrder) {
        this.relateOrder = relateOrder;
    }
 

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getYuyue_time() {
        return yuyue_time;
    }

    public void setYuyue_time(String yuyue_time) {
        this.yuyue_time = yuyue_time;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getActualPeople() {
        return actualPeople;
    }

    public void setActualPeople(Integer actualPeople) {
        this.actualPeople = actualPeople;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getKefu() {
		return kefu;
	}

	public void setKefu(String kefu) {
		this.kefu = kefu;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
