package com.wisdom.living.mapper;

import com.wisdom.living.entity.OfficeMessage;
import org.apache.ibatis.annotations.Param;

/**
 * Created by zbm84 on 2017/12/4.
 */
public interface OfficeMessageMapper {

    Integer insertOfficeMessage(OfficeMessage officeMessage);

    String getOfficePhone(@Param("id")String id);

}
