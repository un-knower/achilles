package com.quancheng.achilles.dao.modelwrite;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

//@Entity
//@Table(name = "tmp_inn_train_log")
public class RestaurantTrainLog implements Serializable {

    private static final long serialVersionUID = -910742865360452644L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Column(name = "restaurant_id")
    @ApiModelProperty(value = "餐厅id")
    private Long              restaurantId;

    @Column(name = "rest_name")
    @ApiModelProperty(value = "餐厅名")
    private String            restName;

    @Column(name = "city_name")
    @ApiModelProperty(value = "城市")
    private String            cityName;

    @Column(name = "address")
    @ApiModelProperty(value = "地址")
    private String            address;

     @Column(name="restuarant_header")
     @ApiModelProperty(value = "负责人")
     private String restaurantHeader;

    @Column(name = "chief_phone")
    @ApiModelProperty(value = "负责人电话")
    private String            chiefPhone;

    @Column(name = "train_date")
    @ApiModelProperty(value = "培训时间")
    private Date              trainDate;

    @Column(name = "feedback")
    @ApiModelProperty(value = "商户信息反馈")
    private String            feedback;

    @Column(name = "train_num")
    @ApiModelProperty(value = "培训人数")
    private String            trainNum;

    @Column(name = "train_type")
    @ApiModelProperty(value = "培训方式")
    private String            trainType;

    @Column(name = "tt_type")
    @ApiModelProperty(value = "台贴")
    private String            ttType;

//    @Column(name = "own_companys")
//    @ApiModelProperty(value = "所属企业")
//    private String            ownCompanys;

    @Column(name = "tc_type")
    @ApiModelProperty(value = "台卡")
    private String            tcType;

    @Column(name = "bq_type")
    @ApiModelProperty(value = "便签")
    private String            bqType;

    @Column(name = "cp_type")
    @ApiModelProperty(value = "优惠同享")
    private String            cpType;

    @Column(name = "count_train")
    @ApiModelProperty(value = "培训次数")
    private String            countTrain;

    @Column(name = "sales_name")
    @ApiModelProperty(value = "销售")
    private String            salesName;

    // 培训原因
    @Column(name = "train_result")
    @ApiModelProperty(value = "培训原因")
    private String            trainResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

     public String getRestaurantHeader() {
         return restaurantHeader;
     }
    
     public void setRestaurantHeader(String restaurantHeader) {
         this.restaurantHeader = restaurantHeader;
     }

    public String getChiefPhone() {
        return chiefPhone;
    }

    public void setChiefPhone(String chiefPhone) {
        this.chiefPhone = chiefPhone;
    }

    public Date getTrainDate() {
        return trainDate;
    }

    public void setTrainDate(Date trainDate) {
        this.trainDate = trainDate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getTtType() {
        return ttType;
    }

    public void setTtType(String ttType) {
        this.ttType = ttType;
    }

//    public String getOwnCompanys() {
//        return ownCompanys;
//    }
//
//    public void setOwnCompanys(String ownCompanys) {
//        this.ownCompanys = ownCompanys;
//    }

    public String getTcType() {
        return tcType;
    }

    public void setTcType(String tcType) {
        this.tcType = tcType;
    }

    public String getBqType() {
        return bqType;
    }

    public void setBqType(String bqType) {
        this.bqType = bqType;
    }

    public String getCpType() {
        return cpType;
    }

    public void setCpType(String cpType) {
        this.cpType = cpType;
    }

    public String getCountTrain() {
        return countTrain;
    }

    public void setCountTrain(String countTrain) {
        this.countTrain = countTrain;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getTrainResult() {
        return trainResult;
    }

    public void setTrainResult(String trainResult) {
        this.trainResult = trainResult;
    }

}
