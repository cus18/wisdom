/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.core.mapper;

import com.wisdom.common.dto.core.user.EasemobDTO;

public interface EasemobMapper {

	EasemobDTO getEasemobToken();

	int updateEasemobToken(EasemobDTO easemobDTO);

}
