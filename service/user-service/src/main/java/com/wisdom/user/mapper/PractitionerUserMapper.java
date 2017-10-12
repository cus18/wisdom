/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.mapper;

import com.wisdom.common.dto.userService.PractitionerUserDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 医护用户DAO接口
 *
 * @author
 * @version 2017年5月9日
 */
public interface PractitionerUserMapper {

    int insertSysPractitionerUser(PractitionerUserDTO sysPractitionerUser);

    int updateSysPractitionerUser(PractitionerUserDTO sysPractitionerUser);

    PractitionerUserDTO getSysPractitioner(String sysUserID);

    Integer updateLoginToken(PractitionerUserDTO sysPractitionerUser);

    PractitionerUserDTO getSysPractitionerByEasemobID(String easemobID);

    PractitionerUserDTO getSysPractitionerByID(String id);

    Page getDoctorListByHospitalID(@Param("sysHospitalID") String sysHospitalID, Page page, @Param("searchValue") String searchValue, @Param("type") String type);

    Integer getDoctorListCountByHospitalID(@Param("sysHospitalID") String sysHospitalID);

    List<String> doctorAndNurseStatistics(String title);
}
