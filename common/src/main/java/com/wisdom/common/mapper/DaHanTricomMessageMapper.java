/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.common.mapper;

import org.apache.ibatis.annotations.Param;

public interface DaHanTricomMessageMapper {


	//插入验证码信息
	public int insertIdentifying(@Param(value = "PhonNum") String PhonNum, @Param(value = "Identifying") String Identifying);

	//查询验证码是否正确
	public int searchIdentify(@Param(value = "PhonNum") String PhonNum, @Param(value = "Identifying") String Identifying);
	

}
