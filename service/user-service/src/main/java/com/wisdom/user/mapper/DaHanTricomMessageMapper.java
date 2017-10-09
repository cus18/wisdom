/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.mapper;

import org.apache.ibatis.annotations.Param;

public interface DaHanTricomMessageMapper {


	//插入验证码信息
	int insertIdentifying(@Param(value = "PhoneNum") String PhoneNum, @Param(value = "Identifying") String Identifying);

	//查询验证码是否正确
	int searchIdentify(@Param(value = "PhoneNum") String PhoneNum, @Param(value = "Identifying") String Identifying);

}
