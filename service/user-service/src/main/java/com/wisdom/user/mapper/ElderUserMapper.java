/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.mapper;

import com.wisdom.common.dto.Page;
import com.wisdom.common.dto.userService.ElderUserDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 会员用户DAO接口
 * @author 张博
 * @version 2017年5月27日
 */
public interface ElderUserMapper {

    int insertSysElderUser(ElderUserDTO sysElderUserDTO);

    ElderUserDTO getSysElder(String sysUserID);

    ElderUserDTO getSysElderUser(String sysElderId);

    Integer updateLoginToken(ElderUserDTO sysElderUserDTO);

    ElderUserDTO getSysElderUserByEasemobID(String easemobID);

    String getSysElderUserMemberCardID();

    void delSysElderUser(String id);

    Page getSysElderUserByHospitalID(@Param("sysHospitalID") String sysHospitalID, Page page);

    Integer getSysElderUserCountByHospitalID(@Param("sysHospitalID") String sysHospitalID);

    Integer getSysElderUserCount();
}
