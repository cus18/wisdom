/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.core.mapper;

import com.wisdom.common.dto.core.entity.RemindEntity;
import com.wisdom.common.dto.core.notification.NotificationMessageDTO;
import org.apache.ibatis.annotations.Param;
import java.util.List;


public interface RemindMapper {

    Integer addRemind(RemindEntity remind);

    List<NotificationMessageDTO> getRemindListBySysElderUserID(@Param("sysElderUserID") String sysElderUserID, @Param("limit") Integer limit);

    NotificationMessageDTO getRemindByID(@Param("ID") String remindID);

}
