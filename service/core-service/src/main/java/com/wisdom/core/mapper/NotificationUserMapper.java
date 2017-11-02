/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.core.mapper;


import com.wisdom.common.dto.core.entity.NotificationUserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 消息用户
 */
public interface NotificationUserMapper {

    Integer addNotificationUser(NotificationUserEntity notificationUser);

    Integer updateNotificationUser(String notificationUserID);

    Integer getNotificationByUnread(@Param("sysElderUserID") String sysElderUserID);


}
