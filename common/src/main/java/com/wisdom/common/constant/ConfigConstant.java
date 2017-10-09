package com.wisdom.common.constant;

import com.wisdom.common.config.Global;

public enum ConfigConstant {

	instance;

	public static final int loginTokenPeriod = Integer.parseInt(Global.getConfig("loginTokenPeriod"));//60*60*24*30;

	public static final String practitionerDomain = Global.getConfig("practitionerDomain");

	public static final String elderVersion = Global.getConfig("elderVersion");

	public static final String practitionerVersion = Global.getConfig("practitionerVersion");

	public static final String hospitalVersion = Global.getConfig("hospitalVersion");



}
