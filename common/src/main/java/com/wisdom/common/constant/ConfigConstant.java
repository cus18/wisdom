package com.wisdom.common.constant;

import com.wisdom.common.config.Global;

public enum ConfigConstant {

	instance;

	public static final int loginTokenPeriod = Integer.parseInt(Global.getConfig("loginTokenPeriod"));//60*60*24*30;

	public static final String practitionerDomain = Global.getConfig("practitionerDomain");

	public static final String elderVersion = Global.getConfig("elderVersion");

	public static final String practitionerVersion = Global.getConfig("practitionerVersion");

	public static final String hospitalVersion = Global.getConfig("hospitalVersion");

    public static final String EASEMOB_MESSAGE_URL = Global.getConfig("easemob.message.url");

	public static final String AMR_TOMP3_FUNC = Global.getConfig("AMR_TOMP3_FUNC");

	public static final String AMR_TOMP3_WINDOWSPATHTEMP = Global.getConfig("AMR_TOMP3_WINDOWSPATHTEMP");

	public static final String AMR_TOMP3_WINDOWSPATH = Global.getConfig("AMR_TOMP3_WINDOWSPATH");

	public static final String AMR_TOMP3_LINUXPATH = Global.getConfig("AMR_TOMP3_LINUXPATH");

	public static final String CORPID = Global.getConfig("CORPID");

	public static final String ANGEL_WEB_URL = Global.getConfig("ANGEL_WEB_URL");

	public static final String TITAN_WEB_URL = Global.getConfig("TITAN_WEB_URL");

	public static final String WISDOM_WEB_URL = Global.getConfig("WISDOM_WEB_URL");

	public static final String KEEPER_WEB_URL = Global.getConfig("KEEPER_WEB_URL");

	public static final String MARKET_WEB_URL = Global.getConfig("MARKET_WEB_URL");

	public static final String SECTET = Global.getConfig("SECTET");

	public static final String DOMAIN_VALUE = Global.getConfig("DOMAIN_VALUE");

	public static final String PARTNER_KEY = Global.getConfig("PARTNER_KEY");

	public static final String APPID = Global.getConfig("APPID");

	public static final String recognitionCallBack = Global.getConfig("recognitionCallBack");

}
