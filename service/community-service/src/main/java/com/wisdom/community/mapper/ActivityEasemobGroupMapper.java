/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.community.mapper;

import com.wisdom.common.dto.basic.ActivityEasemobGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ActivityEasemobGroupMapper {

	Integer addActivityEasemobGroup(ActivityEasemobGroup activityEasemobGroup);

	Integer updateActivityEasemobGroup(ActivityEasemobGroup activityEasemobGroup);

	ActivityEasemobGroup searchActivityEasemobGroupByID(String id);

	List<ActivityEasemobGroup> getUserActivityEasemobGroupList(@Param("elderEasemobID") String elderEasemobID);


	ActivityEasemobGroup searchActivityEasemobGroupByGroupID(@Param("groupId") String groupId);


}
