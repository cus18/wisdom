package com.wisdom.core.service;


import com.wisdom.common.dto.core.Page;
import com.wisdom.common.dto.core.entity.NotificationTemplateEntity;
import com.wisdom.common.dto.core.notification.ExtendMessageDTO;
import com.wisdom.core.mapper.NotificationMapper;
import com.wisdom.core.mapper.NotificationTemplateMapper;
import com.wisdom.core.mapper.NotificationUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zbm84 on 2017/8/17.
 */
@Service
@Transactional(readOnly = false)
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationUserMapper notificationUserMapper;

    @Autowired
    private NotificationTemplateMapper notificationTemplateMapper;

    public List<ExtendMessageDTO> getNotificationListBySysElderUserID(String sysElderUserID, Integer limit) {
        return notificationMapper.getNotificationListBySysElderUserID(sysElderUserID,limit);
    }

    public Integer getNotificationByUnread(String sysElderUserID) {
        return notificationUserMapper.getNotificationByUnread(sysElderUserID);
    }

    public ExtendMessageDTO getNotificationByID(String notificationID) {
        return notificationMapper.getNotificationByID(notificationID);
    }

    public void updateNotificationStatus(String notificationID) {
        notificationUserMapper.updateNotificationUser(notificationID);
    }

    //*************机构版使用接口

    public Integer addNotificationTemplate(NotificationTemplateEntity notificationTemplateEntity) {
        return notificationTemplateMapper.addNotificationTemplate(notificationTemplateEntity);
    }

    public Page getNotificationTemplateList(String searchValue, String sartUpdateDate, String endUpdateDate, Page page) {
        return notificationTemplateMapper.getNotificationTemplateList(searchValue,sartUpdateDate,endUpdateDate,page);
    }

    public NotificationTemplateEntity getNotificationTemplate(String id) {
        return notificationTemplateMapper.getNotificationTemplate(id);
    }

    public Integer updateNotificationTemplate(NotificationTemplateEntity notificationTemplateEntity) {
        return notificationTemplateMapper.updateNotificationTemplate(notificationTemplateEntity);
    }

    public Integer deleteNotificationTemplate(String id) {
        return notificationTemplateMapper.deleteNotificationTemplate(id);
    }

}
