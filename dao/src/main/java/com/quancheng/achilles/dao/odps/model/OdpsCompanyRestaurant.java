package com.quancheng.achilles.dao.odps.model;

import com.quancheng.achilles.dao.annotation.OdpsColumn;

public class OdpsCompanyRestaurant {

    /***/
    @OdpsColumn("time")
    private String  time;
    @OdpsColumn("company_id")
    private String  companyId;
    @OdpsColumn("company_name")
    private String  companyName;
    @OdpsColumn("all_restaurants_num")
    private Integer allRestaurantsNum;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public Integer getAllRestaurantsNum() {
        return allRestaurantsNum;
    }

    public void setAllRestaurantsNum(Integer allRestaurantsNum) {
        this.allRestaurantsNum = allRestaurantsNum;
    }

}
