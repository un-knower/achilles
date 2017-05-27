package com.quancheng.achilles.dao.modelwrite;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "inn_oss_files_mapping")
public class OssFileInfo implements Serializable {

    private static final long serialVersionUID = 2430015295723728978L;
    public static final char OSS_STATUS_SUCCESS = '0';
    public static final char OSS_STATUS_ERROR = '1';
    public static final char OSS_STATUS_OUTDATED = '2';
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "down_model")
    private String downModel;

    @Column(name = "oss_url")
    private String ossUrl;

    @Column(name = "error_msg")
    private String errorMsg;
    /**
     * 0正常-1删除-2失败
     */
    @Column(name = "oss_status")
    private char ossStatus = 0;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "deleted_at")
    private String deletedAt;

    public OssFileInfo(String userName, String ossUrl, String downModel, char ossStatus) {
        super();
        this.userName = userName;
        this.downModel = downModel;
        this.ossUrl = ossUrl;
        this.ossStatus = ossStatus;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createdAt = df.format(new Date());
        this.updatedAt = df.format(new Date());
    }

    public OssFileInfo(String userName, String errorMsg, char ossStatus, String downModel) {
        super();
        this.userName = userName;
        this.errorMsg = errorMsg;
        this.ossStatus = ossStatus;
        this.downModel = downModel;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createdAt = df.format(new Date());
        this.updatedAt = df.format(new Date());
    }

    public OssFileInfo() {
        super();
    }

    public OssFileInfo(String userName) {
        super();
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOssUrl() {
        return ossUrl;
    }

    public void setOssUrl(String ossUrl) {
        this.ossUrl = ossUrl;
    }

    public char getOssStatus() {
        return ossStatus;
    }

    public void setOssStatus(char ossStatus) {
        this.ossStatus = ossStatus;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDownModel() {
        return downModel;
    }

    public void setDownModel(String downModel) {
        this.downModel = downModel;
    }

    @Override
    public String toString() {
        return "OssFileInfo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", downModel='" + downModel + '\'' +
                ", ossUrl='" + ossUrl + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", ossStatus=" + ossStatus +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }
}
