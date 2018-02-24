/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.core.mapper;

import com.wisdom.common.dto.core.user.UserInfoDTO;

import java.util.List;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
public interface UserMapper extends CrudMapper<UserInfoDTO>{

	UserInfoDTO getByLoginName(UserInfoDTO userInfoDTO);

	UserInfoDTO getByOpenId(UserInfoDTO userInfoDTO);

	long findAllCount(UserInfoDTO userInfoDTO);

	int updatePasswordById(UserInfoDTO userInfoDTO);

	int updateLoginInfo(UserInfoDTO userInfoDTO);

	int deleteUserRole(UserInfoDTO userInfoDTO);

	int updateUserInfo(UserInfoDTO userInfoDTO);

	int updateUser(UserInfoDTO userInfoDTO);

	int updateUserOpenIdInfo(UserInfoDTO userInfoDTO);

	int deleteUserOpenIdInfo(UserInfoDTO userInfoDTO);

	List<UserInfoDTO> getUserByInfo(UserInfoDTO userInfoDTO);
}
