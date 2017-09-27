package com.wisdom.common.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class ElderInfoDTO {

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "sysUserId")
    private String sysUserId;

    @JSONField(name = "easemobID")
    private String easemobID;

    @JSONField(name = "easemobPassword")
    private String easemobPassword;

    @JSONField(name = "createDate")
    private String createDate;

    @JSONField(name = "updateDate")
    private String updateDate;

    @JSONField(name = "memberCardID")
    private String memberCardID;

    @JSONField(name = "loginToken")
    private String loginToken;

    @JSONField(name = "sysHospitalId")
    private String sysHospitalId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getEasemobID() {
        return easemobID;
    }

    public void setEasemobID(String easemobID) {
        this.easemobID = easemobID;
    }

    public String getEasemobPassword() {
        return easemobPassword;
    }

    public void setEasemobPassword(String easemobPassword) {
        this.easemobPassword = easemobPassword;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getMemberCardID() {
        return memberCardID;
    }

    public void setMemberCardID(String memberCardID) {
        this.memberCardID = memberCardID;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getSysHospitalId() {
        return sysHospitalId;
    }

    public void setSysHospitalId(String sysHospitalId) {
        this.sysHospitalId = sysHospitalId;
    }
}