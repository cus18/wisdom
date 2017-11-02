/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.core.mapper;

import com.wisdom.common.dto.core.Page;
import com.wisdom.common.dto.core.entity.NotificationTemplateEntity;

/**
 * 消息
 */
public interface NotificationTemplateMapper {

    Integer addNotificationTemplate(NotificationTemplateEntity notificationTemplateEntity);

    Page getNotificationTemplateList(String searchValue, String sartUpdateDate, String endUpdateDate, Page page);

    NotificationTemplateEntity getNotificationTemplate(String id);

    Integer updateNotificationTemplate(NotificationTemplateEntity notificationTemplateEntity);

    Integer deleteNotificationTemplate(String id);
}
