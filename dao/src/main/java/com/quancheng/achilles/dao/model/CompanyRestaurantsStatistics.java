package com.quancheng.achilles.dao.model;

import java.util.Date;

public class CompanyRestaurantsStatistics {

    private Integer id;

    private String  time;

    private Integer companyId;

    private String  companyName;

    private Integer regionId;

    private String  regionName;

    private Integer restaurantsNum;

    private Integer allRestaurantsNum;

    private Date    createdAt;

    private Date    updatedAt;

    private Date    deletedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public Integer getRestaurantsNum() {
        return restaurantsNum;
    }

    public void setRestaurantsNum(Integer restaurantsNum) {
        this.restaurantsNum = restaurantsNum;
    }

    public Integer getAllRestaurantsNum() {
        return allRestaurantsNum;
    }

    public void setAllRestaurantsNum(Integer allRestaurantsNum) {
        this.allRestaurantsNum = allRestaurantsNum;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}
