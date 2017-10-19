package com.wisdom.common.dto.core.user;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by zbm84 on 2017/5/24.
 */
public class LoginDTO {

    @JSONField(name = "loginToken")
    private String loginToken;

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "easemobID")
    private String easemobID;

    @JSONField(name = "easemobPassword")
    private String easemobPassword;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
