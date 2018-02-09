package com.wisdom.community.mapper;

import com.wisdom.common.dto.basic.ActivityDiscuss;
import com.wisdom.common.dto.community.activity.ActivityDiscussDTO;
import com.wisdom.common.dto.community.activity.ActivityDiscussReplyDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by zbm84 on 2017/7/27.
 */
@Repository
public interface ActivityDiscussMapper {

    Integer addActivityDiscuss(ActivityDiscuss activityDiscuss);

    List<ActivityDiscussDTO> getActivityDiscussList(@Param("id") String id, @Param("page") Integer page);

    Integer addActivityDiscussReply(ActivityDiscussReplyDTO activityDiscussReplyDTO);

    List<ActivityDiscussReplyDTO> getActivityDiscussReplyList(@Param("activityDiscussID") String id);

}
