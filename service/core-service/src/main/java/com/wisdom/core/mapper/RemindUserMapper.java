/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.core.mapper;

import com.wisdom.common.dto.core.entity.RemindUserEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 提醒消息用户
 */
public interface RemindUserMapper {

    Integer addRemindUser(RemindUserEntity remindUser);

    Integer updateRemindUser(String remindUserID);

    Integer getRemindByUnread(@Param("sysElderUserID") String sysElderUserID);


}
