package com.quancheng.achilles.dao.quancheng_db.model;

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
    /***/ @Column(name = "order_count")private Integer order_count;
    /***/ @Column(name = "order_sum")private Double orderSum;
    /***/ @Column(name = "order_city")private String order_city;
    /***/ @Column(name = "order_city_id")private Integer order_cityId;
    /***/ @Column(name = "last_order_id")private Integer lastOrderId;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getOrder_count() {
        return order_count;
    }
    public void setOrder_count(Integer order_count) {
        this.order_count = order_count;
    }
    public Double getOrderSum() {
        return orderSum;
    }
    public void setOrderSum(Double orderSum) {
        this.orderSum = orderSum;
    }
    public String getOrder_city() {
        return order_city;
    }
    public void setOrder_city(String order_city) {
        this.order_city = order_city;
    }
    public Integer getOrder_cityId() {
        return order_cityId;
    }
    public void setOrder_cityId(Integer order_cityId) {
        this.order_cityId = order_cityId;
    }
    public Integer getLastOrderId() {
        return lastOrderId;
    }
    public void setLastOrderId(Integer lastOrderId) {
        this.lastOrderId = lastOrderId;
    }
    public OrderStatisticCity(Integer order_count, Double orderSum, String order_city,Integer lastOrderId) {
        super();
        this.order_count = order_count;
        this.orderSum = orderSum;
        this.order_city = order_city;
        this.  lastOrderId=  lastOrderId;
    }
    public OrderStatisticCity() {
        super();
    }
}
