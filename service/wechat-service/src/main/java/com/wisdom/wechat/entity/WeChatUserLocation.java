package com.wisdom.wechat.entity;

import java.util.Date;

/**
 * Created by zbm84 on 2018/4/8.
 */
public class WeChatUserLocation {

    private String openid;
    private String latitude;
    private String longitude;
    private String precisions;
    private Date createTime;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public String getPrecisions() {
        return precisions;
    }

    public void setPrecisions(String precisions) {
        this.precisions = precisions;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = new Date(Long.parseLong(createTime) * 1000);

    }
}
