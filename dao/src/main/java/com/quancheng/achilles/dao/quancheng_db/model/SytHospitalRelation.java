package com.quancheng.achilles.dao.quancheng_db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Table(name = "16860_hospital_clients")
@Entity
public class SytHospitalRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ApiModelProperty(value = "医院ID")
    @Column(name = "hospital_id")
    private Long hospitalId;
    @ApiModelProperty(value = "企业ID")
    @Column(name = "client_id")
    private Long clientId;
    @ApiModelProperty(value = "部门ID")
    @Column(name = "sector_id")
    private Long sectorId;
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
    public Long getHospitalId() {
        return hospitalId;
    }
    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }
    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public Long getSectorId() {
        return sectorId;
    }
    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
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
    public SytHospitalRelation(Long hospitalId, Long clientId,Long sectorId, String createdAt, String updatedAt) {
        super();
        this.sectorId=sectorId;
        this.hospitalId = hospitalId;
        this.clientId = clientId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public SytHospitalRelation() {
        super();
    }
}
