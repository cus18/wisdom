///**
// * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// */
//package com.wisdom.user.mapper;
//
//import org.apache.ibatis.annotations.Param;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Repository;
//
///**
// * 会员用户DAO接口
// * @author 张博
// * @version 2017年5月27日
// */
//public interface HospitalUserMapper {
//
//    int insertSysHospitalUser(HospitalUserDTO sysHospitalUserDTO);
//
//    HospitalUserDTO getSysHospitalUserByUserID(String sysUserID);
//
//    HospitalUserDTO getSysHospitalUserByEasemobID(String hospitalID);
//
//    Integer updateLoginToken(HospitalUserDTO sysHospitalUserDTO);
//
//    Page getSysHospitalUserList(@Param("searchValue") String searchValue, Page page);
//
//    Integer updateSysHospitalUser(HospitalUserDTO sysHospitalUserDTO);
//
//}
