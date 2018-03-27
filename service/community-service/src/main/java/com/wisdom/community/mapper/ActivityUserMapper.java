/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.community.mapper;

import com.wisdom.common.dto.basic.ActivityUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityUserMapper {

	Integer addActivityUser(ActivityUser activityUser);

	/**
	 * 通过活动 ID 获取参与活动总人数
	 * @param activityID
	 * @return
	 */
	Integer getActivityCountByID(@Param("activityID") String activityID, @Param("openID") String openID);

	List<ActivityUser> getActivityUserList(@Param("activityID") String activityID);

}
