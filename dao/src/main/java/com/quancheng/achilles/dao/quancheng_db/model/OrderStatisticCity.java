package com.quancheng.achilles.dao.quancheng_db.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name = "out_order_statistic_city",schema="statistics_db",catalog="statistics_db")
@Entity
public class OrderStatisticCity {
    /***/ @Id @GeneratedValue(strategy = GenerationType.IDENTITY)private Integer id;
    /***/ @Column(name = "order_count")private Integer orderCount=0;
    /***/ @Column(name = "order_sum")private BigDecimal orderSum=BigDecimal.ZERO;
    /***/ @Column(name = "order_city")private String orderCity;
    /***/ @Column(name = "people_sum")private Integer peopleSum=0;
    /***/ @Column(name = "last_order_id")private Integer lastOrderId=0;
     
    public OrderStatisticCity(String orderCity) {
        super();
        this.orderCity = orderCity;
    }

    public OrderStatisticCity(Integer orderCount, BigDecimal orderSum, Integer peopleSum) {
        super();
        this.orderCount = orderCount;
        this.orderSum = orderSum;
        this.peopleSum = peopleSum;
    }

    public OrderStatisticCity() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(BigDecimal orderSum) {
        this.orderSum = orderSum;
    }

    public String getOrderCity() {
        return orderCity;
    }

    public void setOrderCity(String orderCity) {
        this.orderCity = orderCity;
    }

    public Integer getPeopleSum() {
        return peopleSum;
    }

    public void setPeopleSum(Integer peopleSum) {
        this.peopleSum = peopleSum;
    }

    public Integer getLastOrderId() {
        return lastOrderId;
    }

    public void setLastOrderId(Integer lastOrderId) {
        this.lastOrderId = lastOrderId;
    }

}
