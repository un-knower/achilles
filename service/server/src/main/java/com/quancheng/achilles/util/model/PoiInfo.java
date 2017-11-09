package com.quancheng.achilles.util.model;

public class PoiInfo {
    private String address;
    
    private String name;
    
    private String province;
    
    private String city;
    
    private String area;
    
    private String lat;
    
    private String lng;
    //评分
    private String rating;
    //第三方id
    private String thridPartId;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getLat() {
        return lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getLng() {
        return lng;
    }
    public void setLng(String lng) {
        this.lng = lng;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getThridPartId() {
        return thridPartId;
    }
    public void setThridPartId(String thridPartId) {
        this.thridPartId = thridPartId;
    }
}
