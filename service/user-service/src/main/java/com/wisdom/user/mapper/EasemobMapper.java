/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.mapper;

import com.wisdom.user.dto.EasemobDTO;

public interface EasemobMapper {

	EasemobDTO getEasemobToken();

	int updateEasemobToken(EasemobDTO easemobDTO);

}
