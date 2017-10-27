package com.quancheng.achilles.dao.quancheng_db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.annotations.ApiModelProperty;
@Entity
@Table(name = "api_ucb_arc")
public class UcbArcCode {
    @Id
    private Long id;
    @ApiModelProperty(value = "第三方ID")
    @Column(name = "arcid")
    private Long arcid;
    @ApiModelProperty(value = "第三方CODE")
    @Column(name = "arc_code")
    private String arc_code;
    @ApiModelProperty(value = "该 arc_code 可用次数")
    @Column(name = "useable_number")
    private String useable_number;
    @ApiModelProperty(value = "用户ID")
    @Column(name = "user_uid")
    private String user_uid;
    @ApiModelProperty(value = "用户邮箱")
    @Column(name = "user_email")
    private String user_email;
    @ApiModelProperty(value = "用户手机号")
    @Column(name = "user_telephone")
    private String user_telephone;
    @ApiModelProperty(value = "活动名称")
    @Column(name = "activity_name")
    private String activity_name;
    @ApiModelProperty(value = "活动城市")
    @Column(name = "activity_city")
    private String activity_city;
    @ApiModelProperty(value = "活动开始时间， 时间字符串")
    @Column(name = "activity_time_start")
    private String activity_time_start;
    @ApiModelProperty(value = "活动结束时间， 时间字符串")
    @Column(name = "activity_time_end")
    private String activity_time_end;
    @ApiModelProperty(value = "审批状态，0尚未通过审批，1审批通过，2被取消")
    @Column(name = "review_status")
    private String review_status;
    @ApiModelProperty(value = "创建时间")
    @Column(name = "created_at")
    private String created_at;
    @ApiModelProperty(value = "更新时间")
    @Column(name = "updated_at")
    private String updated_at;
    @ApiModelProperty(value = "删除时间")
    @Column(name = "deleted_at")
    private String deleted_at;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getArcid() {
        return arcid;
    }
    public void setArcid(Long arcid) {
        this.arcid = arcid;
    }
    public String getArc_code() {
        return arc_code;
    }
    public void setArc_code(String arc_code) {
        this.arc_code = arc_code;
    }
    public String getUseable_number() {
        return useable_number;
    }
    public void setUseable_number(String useable_number) {
        this.useable_number = useable_number;
    }
    public String getUser_uid() {
        return user_uid;
    }
    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }
    public String getUser_email() {
        return user_email;
    }
    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
    public String getUser_telephone() {
        return user_telephone;
    }
    public void setUser_telephone(String user_telephone) {
        this.user_telephone = user_telephone;
    }
    public String getActivity_name() {
        return activity_name;
    }
    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }
    public String getActivity_city() {
        return activity_city;
    }
    public void setActivity_city(String activity_city) {
        this.activity_city = activity_city;
    }
    public String getActivity_time_start() {
        return activity_time_start;
    }
    public void setActivity_time_start(String activity_time_start) {
        this.activity_time_start = activity_time_start;
    }
    public String getActivity_time_end() {
        return activity_time_end;
    }
    public void setActivity_time_end(String activity_time_end) {
        this.activity_time_end = activity_time_end;
    }
    public String getReview_status() {
        return review_status;
    }
    public void setReview_status(String review_status) {
        this.review_status = review_status;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
    public String getDeleted_at() {
        return deleted_at;
    }
    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}
