package com.wisdom.user.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
public enum StatusConstant {

    INSTANCE;

    @Value("${status.LOGIN_ERROR}")
    public static String LOGIN_ERROR ;

    @Value("${status.SUCCESS}")
    public static String SUCCESS;

    @Value("${status.FAILURE}")
    public static String FAILURE;

    @Value("${status.OFFLINE}")
    public static String OFFLINE;

    @Value("${status.LOGIN_OUT}")
    public String LOGIN_OUT;

    @Value("${status.TOKEN_ERROR}")
    public String TOKEN_ERROR;

    @Value("${status.PARAM_ERROR}")
    public String PARAM_ERROR;

    @Value("${status.MEDICATION_ERROR}")
    public String MEDICATION_ERROR;

    @Value("${status.DIET_ERROR}")
    public String DIET_ERROR;
}
