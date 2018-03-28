package com.quancheng.achilles.dao.ds_st.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dtmp_restaurant_info",schema="cacia")
public class SpecialRestEntity {
    @Id
    /* id */  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /* 高德id */ @Column(name = "gould_id")
    private String gould_id;
    /* 公司id */ @Column(name = "company_id")
    private String company_id;
    /* 餐厅名称 */ @Column(name = "aurant_name")
    private String aurant_name;
    /* 餐厅地址 */ @Column(name = "aurant_address")
    private String aurant_address;
    /* 餐厅所在城市 */ @Column(name = "aurant_city")
    private String aurant_city;
    /* 区 */ @Column(name = "aurant_district")
    private String aurant_district;
    /* 上线状态(0:未上线1:已上线) */ @Column(name = "online_status")
    private String online_status;
    /* 餐厅联系电话 */ @Column(name = "contact_phone")
    private String contact_phone;
    /* 人均消费 */ @Column(name = "price")
    private String price;
    /* 菜系 */ @Column(name = "category")
    private String category;
    /* 营业执照 */ @Column(name = "business_license")
    private String business_license;
    /* 营业执照截止时间 */ @Column(name = "business_endtime")
    private String business_endtime;
    /* 营业执照来源 */ @Column(name = "business_source")
    private String business_source;
    /* 营业许可证 */ @Column(name = "administrative_licensing")
    private String administrative_licensing;
    /* 营业许可证截止时间 */ @Column(name = "licensing_endtime")
    private String licensing_endtime;
    /* 大众点评截图 */ @Column(name = "dianping_screenshots")
    private String dianping_screenshots;
    /* 餐厅图片 */ @Column(name = "indoorUrls")
    private String indoorUrls;
    /* 门头图 */ @Column(name = "outdoorUrl")
    private String outdoorUrl;
    /* 营业许可证来源 */ @Column(name = "licensing_source")
    private String licensing_source;
    /* 申请类型(fast,ordinary) */ @Column(name = "type")
    private String type;
    /* 状态 */ @Column(name = "status")
    private String status;
    /* 餐厅评分 */ @Column(name = "rating")
    private String rating;
    /* 纬度 */ @Column(name = "latitude")
    private String latitude;
    /* 经度 */ @Column(name = "longitude")
    private String longitude;
    /* 创建时间 */ @Column(name = "gmt_created")
    private String gmt_created;
    /* 修改时间 */ @Column(name = "gmt_modified")
    private String gmt_modified;
    /* 备注说明 */ @Column(name = "remark")
    private String remark;
    /* 拒绝说明 */ @Column(name = "refuse_explain")
    private String refuse_explain;
     @Column(name = "custom_column1")
    private String custom_column1;
     @Column(name = "custom_column2")
    private String custom_column2;
     @Column(name = "gonghai_id")
    private String gonghai_id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getGould_id() {
        return gould_id;
    }
    public void setGould_id(String gould_id) {
        this.gould_id = gould_id;
    }
    public String getCompany_id() {
        return company_id;
    }
    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
    public String getAurant_name() {
        return aurant_name;
    }
    public void setAurant_name(String aurant_name) {
        this.aurant_name = aurant_name;
    }
    public String getAurant_address() {
        return aurant_address;
    }
    public void setAurant_address(String aurant_address) {
        this.aurant_address = aurant_address;
    }
    public String getAurant_city() {
        return aurant_city;
    }
    public void setAurant_city(String aurant_city) {
        this.aurant_city = aurant_city;
    }
    public String getAurant_district() {
        return aurant_district;
    }
    public void setAurant_district(String aurant_district) {
        this.aurant_district = aurant_district;
    }
    public String getOnline_status() {
        return online_status;
    }
    public void setOnline_status(String online_status) {
        this.online_status = online_status;
    }
    public String getContact_phone() {
        return contact_phone;
    }
    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getBusiness_license() {
        return business_license;
    }
    public void setBusiness_license(String business_license) {
        this.business_license = business_license;
    }
    public String getBusiness_endtime() {
        return business_endtime;
    }
    public void setBusiness_endtime(String business_endtime) {
        this.business_endtime = business_endtime;
    }
    public String getBusiness_source() {
        return business_source;
    }
    public void setBusiness_source(String business_source) {
        this.business_source = business_source;
    }
    public String getAdministrative_licensing() {
        return administrative_licensing;
    }
    public void setAdministrative_licensing(String administrative_licensing) {
        this.administrative_licensing = administrative_licensing;
    }
    public String getLicensing_endtime() {
        return licensing_endtime;
    }
    public void setLicensing_endtime(String licensing_endtime) {
        this.licensing_endtime = licensing_endtime;
    }
    public String getDianping_screenshots() {
        return dianping_screenshots;
    }
    public void setDianping_screenshots(String dianping_screenshots) {
        this.dianping_screenshots = dianping_screenshots;
    }
    public String getIndoorUrls() {
        return indoorUrls;
    }
    public void setIndoorUrls(String indoorUrls) {
        this.indoorUrls = indoorUrls;
    }
    public String getOutdoorUrl() {
        return outdoorUrl;
    }
    public void setOutdoorUrl(String outdoorUrl) {
        this.outdoorUrl = outdoorUrl;
    }
    public String getLicensing_source() {
        return licensing_source;
    }
    public void setLicensing_source(String licensing_source) {
        this.licensing_source = licensing_source;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getGmt_created() {
        return gmt_created;
    }
    public void setGmt_created(String gmt_created) {
        this.gmt_created = gmt_created;
    }
    public String getGmt_modified() {
        return gmt_modified;
    }
    public void setGmt_modified(String gmt_modified) {
        this.gmt_modified = gmt_modified;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getRefuse_explain() {
        return refuse_explain;
    }
    public void setRefuse_explain(String refuse_explain) {
        this.refuse_explain = refuse_explain;
    }
    public String getCustom_column1() {
        return custom_column1;
    }
    public void setCustom_column1(String custom_column1) {
        this.custom_column1 = custom_column1;
    }
    public String getCustom_column2() {
        return custom_column2;
    }
    public void setCustom_column2(String custom_column2) {
        this.custom_column2 = custom_column2;
    }
    public String getGonghai_id() {
        return gonghai_id;
    }
    public void setGonghai_id(String gonghai_id) {
        this.gonghai_id = gonghai_id;
    }
}
