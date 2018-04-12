package com.quancheng.achilles.dao.quancheng_db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "restaurant_info",catalog="cacia",schema="cacia")
public class SepicalRestaurant {
    /**id*/ @Id  private String id;
    /**高德id*/ @Column(name = "gould_id")private String gouldId;
    /**公司id*/ @Column(name = "company_id")private String companyId;
    /**餐厅名称*/ @Column(name = "aurant_name")private String aurantName;
    /**餐厅地址*/ @Column(name = "aurant_address")private String aurantAddress;
    /**餐厅所在城市*/ @Column(name = "aurant_city")private String aurantCity;
    /**区*/ @Column(name = "aurant_district")private String aurantDistrict;
    /**上线状态(online 上线,offline 下线)*/ @Column(name = "online_status")private String onlineStatus;
    /**餐厅联系电话*/ @Column(name = "contact_phone")private String contactPhone;
    /**人均消费*/ @Column(name = "price")private String price;
    /**菜系*/ @Column(name = "category")private String category;
    /**营业执照*/ @Column(name = "business_license")private String businessLicense;
    /**营业执照截止时间*/ @Column(name = "business_endtime")private Date businessEndtime;
    /**营业执照来源*/ @Column(name = "business_source")private String businessSource;
    /**营业许可证*/ @Column(name = "administrative_licensing")private String administrativeLicensing;
    /**营业许可证截止时间*/ @Column(name = "licensing_endtime")private Date licensingEndtime;
    /**大众点评截图*/ @Column(name = "dianping_screenshots")private String dianpingScreenshots;
    /**餐厅图片*/ @Column(name = "indoorUrls")private String indoorUrls;
    /**门头图*/ @Column(name = "outdoorUrl")private String outdoorUrl;
    /**营业许可证来源*/ @Column(name = "licensing_source")private String licensingSource;
    /**申请类型(fast,ordinary)*/ @Column(name = "type")private String type;
    /**状态(pendingAudit:待审核,beenSubmitted:已提交,notPass:审核不通过,success:审核通过,pendingAutoPass:等待自动审核通过)*/ 
    @Column(name = "status")private String status;
    /**餐厅评分*/ @Column(name = "rating")private String rating;
    /**纬度*/ @Column(name = "latitude")private String latitude;
    /**经度*/ @Column(name = "longitude")private String longitude;
    /**创建时间*/ @Column(name = "gmt_created")private Date gmtCreated;
    /**修改时间*/ @Column(name = "gmt_modified")private Date gmtModified;
    /**备注说明*/ @Column(name = "remark")private String remark;
    /**拒绝说明*/ @Column(name = "refuse_explain")private String refuseExplain;
    /**扩展字段*/ @Column(name = "custom_column1")private String customColumn1;
    /**扩展字段*/ @Column(name = "custom_column2")private String customColumn2;
    /**公海Id*/ @Column(name = "gonghai_id")private String gonghaiId;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getGouldId() {
        return gouldId;
    }
    public void setGouldId(String gouldId) {
        this.gouldId = gouldId;
    }
    public String getCompanyId() {
        return companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public String getAurantName() {
        return aurantName;
    }
    public void setAurantName(String aurantName) {
        this.aurantName = aurantName;
    }
    public String getAurantAddress() {
        return aurantAddress;
    }
    public void setAurantAddress(String aurantAddress) {
        this.aurantAddress = aurantAddress;
    }
    public String getAurantCity() {
        return aurantCity;
    }
    public void setAurantCity(String aurantCity) {
        this.aurantCity = aurantCity;
    }
    public String getAurantDistrict() {
        return aurantDistrict;
    }
    public void setAurantDistrict(String aurantDistrict) {
        this.aurantDistrict = aurantDistrict;
    }
    public String getOnlineStatus() {
        return onlineStatus;
    }
    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
    public String getContactPhone() {
        return contactPhone;
    }
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
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
    public String getBusinessLicense() {
        return businessLicense;
    }
    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }
    public Date getBusinessEndtime() {
        return businessEndtime;
    }
    public void setBusinessEndtime(Date businessEndtime) {
        this.businessEndtime = businessEndtime;
    }
    public String getBusinessSource() {
        return businessSource;
    }
    public void setBusinessSource(String businessSource) {
        this.businessSource = businessSource;
    }
    public String getAdministrativeLicensing() {
        return administrativeLicensing;
    }
    public void setAdministrativeLicensing(String administrativeLicensing) {
        this.administrativeLicensing = administrativeLicensing;
    }
    public Date getLicensingEndtime() {
        return licensingEndtime;
    }
    public void setLicensingEndtime(Date licensingEndtime) {
        this.licensingEndtime = licensingEndtime;
    }
    public String getDianpingScreenshots() {
        return dianpingScreenshots;
    }
    public void setDianpingScreenshots(String dianpingScreenshots) {
        this.dianpingScreenshots = dianpingScreenshots;
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
    public String getLicensingSource() {
        return licensingSource;
    }
    public void setLicensingSource(String licensingSource) {
        this.licensingSource = licensingSource;
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
    public Date getGmtCreated() {
        return gmtCreated;
    }
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }
    public Date getGmtModified() {
        return gmtModified;
    }
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getRefuseExplain() {
        return refuseExplain;
    }
    public void setRefuseExplain(String refuseExplain) {
        this.refuseExplain = refuseExplain;
    }
    public String getCustomColumn1() {
        return customColumn1;
    }
    public void setCustomColumn1(String customColumn1) {
        this.customColumn1 = customColumn1;
    }
    public String getCustomColumn2() {
        return customColumn2;
    }
    public void setCustomColumn2(String customColumn2) {
        this.customColumn2 = customColumn2;
    }
    public String getGonghaiId() {
        return gonghaiId;
    }
    public void setGonghaiId(String gonghaiId) {
        this.gonghaiId = gonghaiId;
    }
}
