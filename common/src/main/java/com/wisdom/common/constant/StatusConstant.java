package com.wisdom.common.constant;

import com.wisdom.common.config.Global;

public enum StatusConstant {

	instance;

	public static final String LOGIN_ERROR = Global.getConfig("LOGIN_ERROR");

	public static final String SUCCESS = Global.getConfig("SUCCESS");

	public static final String FAILURE = Global.getConfig("FAILURE");

	public static final String ONLINE = Global.getConfig("ONLINE");

	public static final String OFFLINE = Global.getConfig("OFFLINE");

	public static final String LOGIN_OUT = Global.getConfig("LOGIN_OUT");

	public static final String TOKEN_ERROR = Global.getConfig("TOKEN_ERROR");

	public static final String PARAM_ERROR = Global.getConfig("PARAM_ERROR");

	public static final String MEDICATION_ERROR = Global.getConfig("MEDICATION_ERROR");

	public static final String DIET_ERROR = Global.getConfig("DIET_ERROR");

}
