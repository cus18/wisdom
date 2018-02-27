/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.community.mapper;

import com.wisdom.common.dto.basic.Activity;
import com.wisdom.common.dto.basic.ActivityFavorite;
import com.wisdom.common.dto.community.activity.ActivityDTO;
import com.wisdom.common.dto.community.activity.AttendedActivityDTO;
import com.wisdom.common.dto.core.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityMapper {

	Integer addActivity(Activity activityDTO);

	List<ActivityDTO> getActivityList(@Param("id") String activityId, @Param("search") String search);

	List<AttendedActivityDTO> getActivityListByElderID(@Param("elderID") String elderID);

	List<ActivityDTO> getMyActivityListByElderID(@Param("openid") String elderID, @Param("pageNo") Integer pageNo, @Param("activityType") String activityType);

	Integer updateActivityStatus(@Param("status") String Status, @Param("id") String id);

	List<Activity> getActivityListByTask();

	List<ActivityFavorite> getActivityFavoriteListByElderID(@Param("sysElderUserID") String sysElderUserID);

	List<ActivityDTO> getMyFavoriteActivityList(@Param("sysElderUserID") String sysElderUserID);

	List<ActivityDTO> getMyHospitalActivityListByHospitalID();

	Activity getActivityByGroupID(String groupID);

	List<Activity> getReadyActivity();

	Integer updateActivityEasemobGroup(@Param("id") String id, @Param("easemobGroupID") String easemobGroupID);

	Integer insertIP(@Param("IP") String IP);

	Page<ActivityDTO> getActivityListByBackEnd(@Param("hospitalID") String hospitalID,
											   @Param("status") String status,
											   @Param("searchValue") String searchValue,
											   @Param("startDate") String startDate,
											   @Param("endDate") String endDate,
											   Page page);

}
