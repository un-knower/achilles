package com.quancheng.achilles.dao.odps.model;

public class OutHospitalRestaurantDistance {

    private Integer id;

    private String  companyId;

    private String  companyName;

    private String  cityId;

    private String  cityName;

    private String  hospitalId;

    private String  hospitalName;

    private Double  hospitalLng;

    private Double  hospitalLat;

    private Integer hospitalSettable;

    private String  restaurantId;

    private String  restaurantName;

    private Double  restaurantLng;

    private Double  restaurantLat;

    private Integer restaurantSettable;

    private Integer supportWaimai;

    private Integer supportReserve;

    private String  cookStyle;

    private Double  consume;

    private Integer boxNum;

    private Integer period;

    private String  rateSettlementType;

    private String  manageType;

    private Double  shippingDis;

    private Double  distance;

    private Integer isWithin;
    private String  hospitalAddress;

    private String  restaurantAddress;

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress == null ? null : hospitalAddress.trim();
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress == null ? null : restaurantAddress.trim();
    }

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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId == null ? null : hospitalId.trim();
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName == null ? null : hospitalName.trim();
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

    public Integer getHospitalSettable() {
        return hospitalSettable;
    }

    public void setHospitalSettable(Integer hospitalSettable) {
        this.hospitalSettable = hospitalSettable;
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

    public Integer getRestaurantSettable() {
        return restaurantSettable;
    }

    public void setRestaurantSettable(Integer restaurantSettable) {
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
}
