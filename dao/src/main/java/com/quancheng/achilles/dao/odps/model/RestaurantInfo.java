package com.quancheng.achilles.dao.odps.model;

public class RestaurantInfo {

    private Integer id;

    private String  companyId;

    private String  companyName;

    private String  cityId;

    private String  cityName;

    private String  restaurantId;

    private String  restaurantName;

    private Double  lng;

    private Double  lat;

    private Integer settable;

    private Integer supportWaimai;

    private Integer supportReserve;

    private String  cookStyle;

    private Double  consume;

    private Integer boxNum;

    private Integer period;

    private String  rateSettlementType;

    private String  manageType;

    private Double  shippingDis;

    private String  address;

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
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId == null ? null : cityId.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId == null ? null : restaurantId.trim();
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName == null ? null : restaurantName.trim();
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getSettable() {
        return settable;
    }

    public void setSettable(Integer settable) {
        this.settable = settable;
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
        this.cookStyle = cookStyle == null ? null : cookStyle.trim();
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

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getRateSettlementType() {
        return rateSettlementType;
    }

    public void setRateSettlementType(String rateSettlementType) {
        this.rateSettlementType = rateSettlementType == null ? null : rateSettlementType.trim();
    }

    public String getManageType() {
        return manageType;
    }

    public void setManageType(String manageType) {
        this.manageType = manageType == null ? null : manageType.trim();
    }

    public Double getShippingDis() {
        return shippingDis;
    }

    public void setShippingDis(Double shippingDis) {
        this.shippingDis = shippingDis;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}
