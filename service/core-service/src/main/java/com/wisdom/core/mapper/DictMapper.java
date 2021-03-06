/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.core.mapper;

import com.wisdom.common.dto.core.DictDTO;
import com.wisdom.common.dto.core.user.UserInfoDTO;

import java.util.List;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
public interface DictMapper {

	List<DictDTO> findListByInfo(DictDTO dictDTO);
}
