package com.quancheng.achilles.dao.modelwrite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
@Entity
@Table(name = "tmp_client_query")
public class QCClient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    @Column(name = "client_name")
    @ApiModelProperty(value = "企业")
    private String clientName;
    
    @Column(name = "city")
    @ApiModelProperty(value = "城市")
    private String city;
    
    @Column(name = "branch")
    @ApiModelProperty(value = "分公司")
    private String branch;
    
    @Column(name = "bus")
    @ApiModelProperty(value = "事业部")
    private String bus;
    
    @Column(name = "sector")
    @ApiModelProperty(value = "部门")
    private String sector;
    
    @Column(name = "productgroup")
    @ApiModelProperty(value = "产品组")
    private String productgroup;
    
    @Column(name = "cost_center")
    @ApiModelProperty(value = "成本中心")
    private String costCenter;
    
    @Column(name = "region")
    @ApiModelProperty(value = "大区")
    private String region;
    
    @Column(name = "count_money")
    @ApiModelProperty(value = "订单总金额")
    private String countMoney;
    
    @Column(name = "count_user")
    @ApiModelProperty(value = "用户总数")
    private String countUser;
    
    @Column(name = "count_rest")
    @ApiModelProperty(value = "在线餐厅数")
    private String countRest;
    
    @Column(name = "count_order")
    @ApiModelProperty(value = "订单总数")
    private String countOrder;
    
    @Column(name = "rate_user_order")
    @ApiModelProperty(value = "用户下单率")
    private String rateUserOrder;
    
//    @Column(name = "rate_user_use")
//    @ApiModelProperty(value = "用户使用率")
//    private String rateUserUse;
    
    @Column(name = "count_rest_recom")
    @ApiModelProperty(value = "推荐餐厅")
    private String countRestCostRecommender;
    
    @Column(name = "count_rest_recom_ol")
    @ApiModelProperty(value = "已上线推荐餐厅数")
    private String restRecommenderOl;
    
    @Column(name = "rate_rest_recommender_use")
    @ApiModelProperty(value = "推荐餐厅使用率")
    private String rateRestRecommenderUse;
    
    @Column(name = "count_rest_had_cost")
    @ApiModelProperty(value = "有订单的餐厅数")
    private String countRestCost;
    
    @Column(name = "rate_rest_use")
    @ApiModelProperty(value = "餐厅使用率")
    private String rateRestUse;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getProductgroup() {
        return productgroup;
    }

    public void setProductgroup(String productgroup) {
        this.productgroup = productgroup;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountMoney() {
        return countMoney;
    }

    public void setCountMoney(String countMoney) {
        this.countMoney = countMoney;
    }

    public String getCountUser() {
        return countUser;
    }

    public void setCountUser(String countUser) {
        this.countUser = countUser;
    }

    public String getCountRest() {
        return countRest;
    }

    public void setCountRest(String countRest) {
        this.countRest = countRest;
    }

    public String getCountOrder() {
        return countOrder;
    }

    public void setCountOrder(String countOrder) {
        this.countOrder = countOrder;
    }

    public String getRateUserOrder() {
        return rateUserOrder;
    }

    public void setRateUserOrder(String rateUserOrder) {
        this.rateUserOrder = rateUserOrder;
    }
//
//    public String getRateUserUse() {
//        return rateUserUse;
//    }
//
//    public void setRateUserUse(String rateUserUse) {
//        this.rateUserUse = rateUserUse;
//    }

    public String getCountRestCostRecommender() {
        return countRestCostRecommender;
    }

    public void setCountRestCostRecommender(String countRestCostRecommender) {
        this.countRestCostRecommender = countRestCostRecommender;
    }

    public String getRestRecommenderOl() {
        return restRecommenderOl;
    }

    public void setRestRecommenderOl(String restRecommenderOl) {
        this.restRecommenderOl = restRecommenderOl;
    }

    public String getRateRestRecommenderUse() {
        return rateRestRecommenderUse;
    }

    public void setRateRestRecommenderUse(String rateRestRecommenderUse) {
        this.rateRestRecommenderUse = rateRestRecommenderUse;
    }

    public String getCountRestCost() {
        return countRestCost;
    }

    public void setCountRestCost(String countRestCost) {
        this.countRestCost = countRestCost;
    }

    public String getRateRestUse() {
        return rateRestUse;
    }

    public void setRateRestUse(String rateRestUse) {
        this.rateRestUse = rateRestUse;
    }
}
