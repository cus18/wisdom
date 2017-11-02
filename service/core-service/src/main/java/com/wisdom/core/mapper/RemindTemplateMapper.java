package com.wisdom.core.mapper;

import com.wisdom.common.dto.core.Page;
import com.wisdom.common.dto.core.entity.RemindTemplateEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 提醒模板
 */

public interface RemindTemplateMapper {

    Integer addRemindTemplate(RemindTemplateEntity remindTemplateEntity);

    RemindTemplateEntity getRemindTemplateEntityByID(@Param("ID") String remindTemplateID);

    Page getRemindTemplateEntityList(String title, String sartUpdateDate, String endUpdateDate, Page page);

    Integer updateRemindTemplate(RemindTemplateEntity remindTemplateEntity);

    Integer deleteRemindTemplate(String id);

}
