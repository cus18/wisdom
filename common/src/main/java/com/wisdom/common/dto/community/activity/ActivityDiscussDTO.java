package com.wisdom.common.dto.community.activity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class ActivityDiscussDTO {

    //此条评论的对应的活动ID
    @JSONField(name = "activityId")
    private String activityId;

    //此条评论的评论者的logo
    @JSONField(name = "weChatHeadPhoto")
    private String weChatHeadPhoto;

    //此条评论的评论者的名称
    @JSONField(name = "weChatNickName")
    private String wechatNickName;

    //此条评论的评论者的ID号
    @JSONField(name = "openID")
    private String openID;

    //此条评论的创建时间
    @JSONField(name = "discussDate")
    private Date discussDate;

    //此条评论的创建时间
    @JSONField(name = "discussContent")
    private String discussContent;



    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getWeChatHeadPhoto() {
        return weChatHeadPhoto;
    }

    public void setWeChatHeadPhoto(String weChatHeadPhoto) {
        this.weChatHeadPhoto = weChatHeadPhoto;
    }

    public String getWechatNickName() {
        return wechatNickName;
    }

    public void setWechatNickName(String wechatNickName) {
        this.wechatNickName = wechatNickName;
    }

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public Date getDiscussDate() {
        return discussDate;
    }

    public void setDiscussDate(Date discussDate) {
        this.discussDate = discussDate;
    }

    public String getDiscussContent() {
        return discussContent;
    }

    public void setDiscussContent(String discussContent) {
        this.discussContent = discussContent;
    }
}