package com.wisdom.common.dto.core.user;

/**
 * Created by zbm84 on 2017/5/8.
 */
public class EasemobDTO {

    private String accessToken;

    private String expiresIn;

    private String application;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
