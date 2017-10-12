/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.mapper;

import com.wisdom.common.dto.userService.EasemobGroupDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 医护用户DAO接口
 * @author
 * @version 2017年5月9日
 */
public interface EasemobGroupMapper {

    int insertEasemobGroup(EasemobGroupDTO easemobGroup);

    EasemobGroupDTO getEasemobGroupByGroupID(@Param("easemobGroupID") String groupID);

    EasemobGroupDTO getEasemobGroupIDByElderID(@Param("elderEasemobID") String elderEasemobID);

    List<EasemobGroupDTO> getEasemobGroupByDoctorEasemobID(@Param("doctorEasemobID") String doctorEasemobID);

    int updateEasemobGroup(EasemobGroupDTO easemobGroup);

}
