package com.wisdom.living.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.wisdom.common.dto.health.RelativeDTO;

import java.util.Date;
import java.util.List;

public class LivingServiceDTO {

    @JSONField(name = "LivingServiceId")
    private String LivingServiceId;

    @JSONField(name = "LivingServiceName")
    private String LivingServiceName;

    @JSONField(name = "LivingServiceDescription")
    private String LivingServiceDescription;

    @JSONField(name = "LivingServicePriceValue")
    private String LivingServicePriceValue;

    @JSONField(name = "LivingServicePriceUnit")
    private String LivingServicePriceUnit;

    @JSONField(name = "LivingServiceProvider")
    private String LivingServiceProvider;

    @JSONField(name = "LivingServiceType")
    private String LivingServiceType;

    @JSONField(name = "LivingServiceSpecial")
    private String LivingServiceSpecial;

    @JSONField(name = "LivingServiceNum")
    private String LivingServiceNum;

    @JSONField(name = "LivingServiceURL")
    private String LivingServiceURL;

    public String getLivingServiceId() {
        return LivingServiceId;
    }

    public void setLivingServiceId(String livingServiceId) {
        LivingServiceId = livingServiceId;
    }

    public String getLivingServiceName() {
        return LivingServiceName;
    }

    public void setLivingServiceName(String livingServiceName) {
        LivingServiceName = livingServiceName;
    }

    public String getLivingServiceDescription() {
        return LivingServiceDescription;
    }

    public void setLivingServiceDescription(String livingServiceDescription) {
        LivingServiceDescription = livingServiceDescription;
    }

    public String getLivingServicePriceValue() {
        return LivingServicePriceValue;
    }

    public void setLivingServicePriceValue(String livingServicePriceValue) {
        LivingServicePriceValue = livingServicePriceValue;
    }

    public String getLivingServicePriceUnit() {
        return LivingServicePriceUnit;
    }

    public void setLivingServicePriceUnit(String livingServicePriceUnit) {
        LivingServicePriceUnit = livingServicePriceUnit;
    }

    public String getLivingServiceProvider() {
        return LivingServiceProvider;
    }

    public void setLivingServiceProvider(String livingServiceProvider) {
        LivingServiceProvider = livingServiceProvider;
    }

    public String getLivingServiceType() {
        return LivingServiceType;
    }

    public void setLivingServiceType(String livingServiceType) {
        LivingServiceType = livingServiceType;
    }

    public String getLivingServiceSpecial() {
        return LivingServiceSpecial;
    }

    public void setLivingServiceSpecial(String livingServiceSpecial) {
        LivingServiceSpecial = livingServiceSpecial;
    }

    public String getLivingServiceNum() {
        return LivingServiceNum;
    }

    public void setLivingServiceNum(String livingServiceNum) {
        LivingServiceNum = livingServiceNum;
    }

    public String getLivingServiceURL() {
        return LivingServiceURL;
    }

    public void setLivingServiceURL(String livingServiceURL) {
        LivingServiceURL = livingServiceURL;
    }
}