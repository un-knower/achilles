package com.quancheng.achilles.dao.odps.model;

import javax.persistence.Column;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

public class OutHospitalRestaurantDistance {

    @Id
    private Integer id;
    @ApiModelProperty(value = "公司ID")
    @Column(name = "companyId")
    private String  companyId;
    @ApiModelProperty(value = "公司名")
    @Column(name = "companyName")
    private String  companyName;
    @ApiModelProperty(value = "城市ID")
    @Column(name = "cityId")
    private String  cityId;
    @ApiModelProperty(value = "城市名")
    @Column(name = "cityName")
    private String  cityName;
    @ApiModelProperty(value = "医院ID")
    @Column(name = "hospitalId")
    private String  hospitalId;
    @ApiModelProperty(value = "医院名")
    @Column(name = "hospitalName")
    private String  hospitalName;
    @ApiModelProperty(value = "医院经度")
    @Column(name = "hospitalLng")
    private Double  hospitalLng;
    @ApiModelProperty(value = "医院纬度")
    @Column(name = "hospitalLat")
    private Double  hospitalLat;
    @ApiModelProperty(value = "医院是否可定位")
    @Column(name = "hospitalSettable")
    private String  hospitalSettable;
    @ApiModelProperty(value = "餐厅ID")
    @Column(name = "restaurantId")
    private String  restaurantId;
    @ApiModelProperty(value = "餐厅名")
    @Column(name = "restaurantName")
    private String  restaurantName;
    @ApiModelProperty(value = "餐厅经度")
    @Column(name = "restaurantLng")
    private Double  restaurantLng;
    @ApiModelProperty(value = "餐厅纬度")
    @Column(name = "restaurantLat")
    private Double  restaurantLat;
    @ApiModelProperty(value = "餐厅是否可定位")
    @Column(name = "restaurantSettable")
    private String  restaurantSettable;
    @ApiModelProperty(value = "是否支持外卖")
    @Column(name = "supportWaimai")
    private Integer supportWaimai;
    @ApiModelProperty(value = "是否支持预定")
    @Column(name = "supportReserve")
    private Integer supportReserve;
    @ApiModelProperty(value = "菜系")
    @Column(name = "cookStyle")
    private String  cookStyle;
    @ApiModelProperty(value = "人均")
    @Column(name = "consume")
    private Double  consume;
    @ApiModelProperty(value = "包厢数")
    @Column(name = "boxNum")
    private Integer boxNum;
    @ApiModelProperty(value = "账期（天）")
    @Column(name = "period")
    private Double  period;
    @ApiModelProperty(value = "返点结算类型")
    @Column(name = "rateSettlementType")
    private String  rateSettlementType;
    @ApiModelProperty(value = "结算类型")
    @Column(name = "manageType")
    private String  manageType;
    @ApiModelProperty(value = "配送距离")
    @Column(name = "shippingDis")
    private Double  shippingDis;
    @ApiModelProperty(value = "医院餐厅距离")
    @Column(name = "distance")
    private Double  distance;
    @ApiModelProperty(value = "外卖是否在可配送范围")
    @Column(name = "isWithin")
    private Integer isWithin;
    @ApiModelProperty(value = "医院地址")
    @Column(name = "hospitalAddress")
    private String  hospitalAddress;
    @ApiModelProperty(value = "餐厅地址")
    @Column(name = "restaurantAddress")
    private String  restaurantAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Double getHospitalLng() {
        return hospitalLng;
    }

    public void setHospitalLng(Double hospitalLng) {
        this.hospitalLng = hospitalLng;
    }

    public Double getHospitalLat() {
        return hospitalLat;
    }

    public void setHospitalLat(Double hospitalLat) {
        this.hospitalLat = hospitalLat;
    }

    public String getHospitalSettable() {
        return hospitalSettable;
    }

    public void setHospitalSettable(String hospitalSettable) {
        this.hospitalSettable = hospitalSettable;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Double getRestaurantLng() {
        return restaurantLng;
    }

    public void setRestaurantLng(Double restaurantLng) {
        this.restaurantLng = restaurantLng;
    }

    public Double getRestaurantLat() {
        return restaurantLat;
    }

    public void setRestaurantLat(Double restaurantLat) {
        this.restaurantLat = restaurantLat;
    }

    public String getRestaurantSettable() {
        return restaurantSettable;
    }

    public void setRestaurantSettable(String restaurantSettable) {
        this.restaurantSettable = restaurantSettable;
    }

    public Integer getSupportWaimai() {
        return supportWaimai;
    }

    public void setSupportWaimai(Integer supportWaimai) {
        this.supportWaimai = supportWaimai;
    }

    public Integer getSupportReserve() {
        return supportReserve;
    }

    public void setSupportReserve(Integer supportReserve) {
        this.supportReserve = supportReserve;
    }

    public String getCookStyle() {
        return cookStyle;
    }

    public void setCookStyle(String cookStyle) {
        this.cookStyle = cookStyle;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }

    public Integer getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(Integer boxNum) {
        this.boxNum = boxNum;
    }

    public Double getPeriod() {
        return period;
    }

    public void setPeriod(Double period) {
        this.period = period;
    }

    public String getRateSettlementType() {
        return rateSettlementType;
    }

    public void setRateSettlementType(String rateSettlementType) {
        this.rateSettlementType = rateSettlementType;
    }

    public String getManageType() {
        return manageType;
    }

    public void setManageType(String manageType) {
        this.manageType = manageType;
    }

    public Double getShippingDis() {
        return shippingDis;
    }

    public void setShippingDis(Double shippingDis) {
        this.shippingDis = shippingDis;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getIsWithin() {
        return isWithin;
    }

    public void setIsWithin(Integer isWithin) {
        this.isWithin = isWithin;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

}
