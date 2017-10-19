package com.wisdom.community.mapper;

import com.wisdom.common.dto.core.Page;
import com.wisdom.common.dto.community.course.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunxiao on 2017/8/8.
 */
@Repository
public interface CourseMapper {

    Page getLiveCourseByInfo(LiveCourseDTO liveCourseDTO, Page page);

    List<LiveCourseDTO> getAllLiveCourseByInfo(LiveCourseDTO liveCourseDTO);

    LiveCourseDTO getLiveBroadCastDetail(LiveCourseDTO dto);

    void saveLiveCourseRegister(LiveCourseRegisterDTO dto);

    Page getOnlineCourseList(OnlineCourseDTO onlineCourseDTO, Page page);

    OnlineCourseDTO getOnlineCourse(OnlineCourseDTO onlineCourseDTO);

    List<OnlineCourseDataDTO> getOnlineCourseDataList(OnlineCourseDataDTO onlineCourseDataDTO);

    List<OnlineCourseDiscussDTO> getOnlineCourseDiscuss(OnlineCourseDiscussDTO dto, Page page);

    void addOnlineCourseDiscuss(OnlineCourseDiscussDTO onlineCourseDiscussDTO);

    void updateLiveCourse(LiveCourseDTO liveCourseDTO);

    LiveCourseRegisterDTO getLiveCourseRegister(LiveCourseRegisterDTO liveCourseRegisterDTO);

    Page getMyOnlineCourse(OnlineCourseMyCourseDTO dto, Page page);

    List<OnlineCourseDTO> getAllOnlineCourseListByInfo(OnlineCourseDTO dto);

    List<OnlineCourseMyCourseDTO> getAllMyOnlineCourseByInfo(OnlineCourseMyCourseDTO dto);
}
