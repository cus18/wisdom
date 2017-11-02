/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.core.mapper;


import com.wisdom.common.dto.core.entity.NotificationEntity;
import com.wisdom.common.dto.core.notification.ExtendMessageDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 消息
 */
public interface NotificationMapper {

    Integer addNotification(NotificationEntity notification);

    List<ExtendMessageDTO> getNotificationListBySysElderUserID(@Param("sysElderUserID") String sysElderUserID, @Param("limit") Integer limit);

    ExtendMessageDTO getNotificationByID(@Param("ID") String notificationID);



}
