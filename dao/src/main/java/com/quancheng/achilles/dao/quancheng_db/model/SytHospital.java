package com.quancheng.achilles.dao.quancheng_db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Table(name = "16860_hospitals")
@Entity
public class SytHospital {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ApiModelProperty(value = "用户id")
    @Column(name = "user_id")
    private Integer userId;
    @ApiModelProperty(value = "医院名称")
    @Column(name = "name")
    private String name;
    @ApiModelProperty(value = "级别")
    @Column(name = "level")
    private String level;
    @ApiModelProperty(value = "省")
    @Column(name = "province")
    private Integer province;
    @ApiModelProperty(value = "市")
    @Column(name = "city")
    private Integer city;
    @ApiModelProperty(value = "区")
    @Column(name = "area")
    private Integer area;
    @ApiModelProperty(value = "地址")
    @Column(name = "address")
    private String address;
    @ApiModelProperty(value = "联系信息")
    @Column(name = "contact")
    private String contact;
    @ApiModelProperty(value = "状态 0禁用 1启用")
    @Column(name = "status")
    private Integer status;
    @ApiModelProperty(value = "")
    @Column(name = "lng")
    private String lng;
    @ApiModelProperty(value = "")
    @Column(name = "lat")
    private String lat;
    @ApiModelProperty(value = "医院类型")
    @Column(name = "type")
    private String type;
    @ApiModelProperty(value = "创建时间")
    @Column(name = "created_at")
    private String createdAt;
    @ApiModelProperty(value = "更新时间")
    @Column(name = "updated_at")
    private String updatedAt;
    @ApiModelProperty(value = "删除时间")
    @Column(name = "deleted_at")
    private String deletedAt;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
 
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public Integer getProvince() {
        return province;
    }
    public void setProvince(Integer province) {
        this.province = province;
    }
    public Integer getCity() {
        return city;
    }
    public void setCity(Integer city) {
        this.city = city;
    }
    public Integer getArea() {
        return area;
    }
    public void setArea(Integer area) {
        this.area = area;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getLng() {
        return lng;
    }
    public void setLng(String lng) {
        this.lng = lng;
    }
    public String getLat() {
        return lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getDeletedAt() {
        return deletedAt;
    }
    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
    @Override
    public String toString() {
        return "SytHospital [id=" + id + ", userId=" + userId + ", name=" + name + ", level=" + level + ", province="
                + province + ", city=" + city + ", area=" + area + ", address=" + address + ", contact=" + contact
                + ", status=" + status + ", lng=" + lng + ", lat=" + lat + ", type=" + type + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + "]";
    }
}
