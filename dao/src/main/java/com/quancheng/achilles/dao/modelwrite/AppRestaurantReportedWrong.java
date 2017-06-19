package com.quancheng.achilles.dao.modelwrite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "out_app_restaurant_reported_wrong")
public class AppRestaurantReportedWrong {
    @Id
    @ApiModelProperty(value = "id")
    private String uuid;
    @ApiModelProperty(value = "餐厅反馈表id")
    @Column(name = "id")
    private String id;
    @ApiModelProperty(value = "所属企业")
    @Column(name = "title")
    private String title;
    @Column(name = "name_num")
    private String nameNum;
    @ApiModelProperty(value = "纠错人")
    @Column(name = "username")
    private String username;
    @ApiModelProperty(value = "纠错人姓名")
    @Column(name = "realname")
    private String realname;
    @ApiModelProperty(value = "纠错时间")
    @Column(name = "create_time")
    private String createTime;
    @ApiModelProperty(value = "纠错人手机")
    @Column(name = "mobile")
    private String mobile;
    @ApiModelProperty(value = "餐厅ID")
    @Column(name = "restaurant_id")
    private String restaurantId;
    @ApiModelProperty(value = "餐厅名称")
    @Column(name = "store_name")
    private String storeName;
    @ApiModelProperty(value = "城市ID")
    @Column(name = "city")
    private String city;
    @ApiModelProperty(value = "城市名称")
    @Column(name = "name")
    private String name;
    @ApiModelProperty(value = "纠错内容")
    @Column(name = "content")
    private String content;
    @ApiModelProperty(value = "BD维护销售")
    @Column(name = "user_name")
    private String saleUserName;
    @ApiModelProperty(value = "大区ID")
    @Column(name = "region_id")
    private String regionId;
    @ApiModelProperty(value = "大区")
    @Column(name = "city_name")
    private String cityName;
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getNameNum() {
        return nameNum;
    }
    public void setNameNum(String nameNum) {
        this.nameNum = nameNum;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRealname() {
        return realname;
    }
    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSaleUserName() {
        return saleUserName;
    }
    public void setSaleUserName(String saleUserName) {
        this.saleUserName = saleUserName;
    }
    public String getRegionId() {
        return regionId;
    }
    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
