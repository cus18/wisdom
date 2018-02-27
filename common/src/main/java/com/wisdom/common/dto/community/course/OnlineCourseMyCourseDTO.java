package com.wisdom.common.dto.community.course;

/**
 * Created by sunxiao on 2017/8/16.
 */
public class OnlineCourseMyCourseDTO {

    private Integer myid;

    private String elderId;

    private String openid;

    private Integer courseId;

    private String type;

    private OnlineCourseDTO onlineCourseDTO;


    public Integer getMyid() {
        return myid;
    }

    public void setMyid(Integer myid) {
        this.myid = myid;
    }

    public String getElderId() {
        return elderId;
    }

    public void setElderId(String elderId) {
        this.elderId = elderId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public OnlineCourseDTO getOnlineCourseDTO() {
        return onlineCourseDTO;
    }

    public void setOnlineCourseDTO(OnlineCourseDTO onlineCourseDTO) {
        this.onlineCourseDTO = onlineCourseDTO;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
