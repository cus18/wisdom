package com.wisdom.living.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class LivingServiceOrderDTO {

    @JSONField(name = "LivingServiceOrderId")
    private String LivingServiceOrderId;

    @JSONField(name = "LivingService")
    private LivingServiceDTO livingServiceDTO;

    @JSONField(name = "LivingServiceOrderTime")
    private String LivingServiceOrderTime;

    @JSONField(name = "LivingServiceStatus")
    private String LivingServiceStatus;

    @JSONField(name = "rejectReason")
    private String rejectReason;

    @JSONField(name = "serviceProviderName")
    private String serviceProviderName;

    public String getLivingServiceOrderId() {
        return LivingServiceOrderId;
    }

    public void setLivingServiceOrderId(String livingServiceOrderId) {
        LivingServiceOrderId = livingServiceOrderId;
    }

    public LivingServiceDTO getLivingServiceDTO() {
        return livingServiceDTO;
    }

    public void setLivingServiceDTO(LivingServiceDTO livingServiceDTO) {
        this.livingServiceDTO = livingServiceDTO;
    }

    public String getLivingServiceOrderTime() {
        return LivingServiceOrderTime;
    }

    public void setLivingServiceOrderTime(String livingServiceOrderTime) {
        LivingServiceOrderTime = livingServiceOrderTime;
    }

    public String getLivingServiceStatus() {
        return LivingServiceStatus;
    }

    public void setLivingServiceStatus(String livingServiceStatus) {
        LivingServiceStatus = livingServiceStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }
}