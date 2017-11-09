package com.quancheng.achilles.dao.ds_st.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by XZW on 2016/9/7 0007.
 */
@Entity
@Table(name = "tmp_member")
public class Member  {

    @Id
    @ApiModelProperty(value = "ID")
    private Long            id;

    private Long cid;
    
    @ApiModelProperty(value = "用户姓名")
    private String          realname;

    @Column(name = "job_num")
    @ApiModelProperty(value = "工号")
    private String          jobNum;

    @Column(name = "username")
    @ApiModelProperty(value = "用户名")
    private String          username;

    @ApiModelProperty(value = "邮箱")
    private String          email;

    @ApiModelProperty(value = "手机号")
    private String          mobile;

    
    private String          member_status;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMember_status() {
        return member_status;
    }

    public void setMember_status(String member_status) {
        this.member_status = member_status;
    }
}
