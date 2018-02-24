package com.wisdom.common.dto.community.course;

import com.alibaba.fastjson.annotation.JSONField;
import com.wisdom.common.dto.basic.DataEntity;
import java.util.Date;

public class OnlineCourseDiscussDTO extends DataEntity<OnlineCourseDiscussDTO> {

    //评论id
    @JSONField(name = "discussId")
    private Integer discussId;

    //在线课程ID号
    @JSONField(name = "onlineCourseId")
    private Integer onlineCourseId;

    //评价者的ID号
    @JSONField(name = "opendId")
    private String opendId;

    //评价者的名字
    @JSONField(name = "wechatName")
    private String wechatName;

    //评价者的名字
    @JSONField(name = "wechatHeadPhoto")
    private String wechatHeadPhoto;

    //评论的内容
    @JSONField(name = "onlineCourseDiscussContent")
    private String onlineCourseDiscussContent;

    //评论的时间
    @JSONField(name = "onlineCourseDiscussDate")
    private String onlineCourseDiscussDate;


    public Integer getDiscussId() {
        return discussId;
    }

    public void setDiscussId(Integer discussId) {
        this.discussId = discussId;
    }

    public Integer getOnlineCourseId() {
        return onlineCourseId;
    }

    public void setOnlineCourseId(Integer onlineCourseId) {
        this.onlineCourseId = onlineCourseId;
    }

    public String getOpendId() {
        return opendId;
    }

    public void setOpendId(String opendId) {
        this.opendId = opendId;
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName;
    }

    public String getWechatHeadPhoto() {
        return wechatHeadPhoto;
    }

    public void setWechatHeadPhoto(String wechatHeadPhoto) {
        this.wechatHeadPhoto = wechatHeadPhoto;
    }

    public String getOnlineCourseDiscussContent() {
        return onlineCourseDiscussContent;
    }

    public void setOnlineCourseDiscussContent(String onlineCourseDiscussContent) {
        this.onlineCourseDiscussContent = onlineCourseDiscussContent;
    }

    public String getOnlineCourseDiscussDate() {
        return onlineCourseDiscussDate;
    }

    public void setOnlineCourseDiscussDate(String onlineCourseDiscussDate) {
        this.onlineCourseDiscussDate = onlineCourseDiscussDate;
    }
}