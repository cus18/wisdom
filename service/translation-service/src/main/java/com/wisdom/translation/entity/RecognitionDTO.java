package com.wisdom.translation.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class RecognitionDTO {

    @JSONField(name = "format")
    private String format;

    @JSONField(name = "rate")
    private long rate;

    @JSONField(name = "channel")
    private String channel;

    @JSONField(name = "url")
    private String url;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}