package com.wisdom.common.dto.community.activity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

public class ActivityDiscussDTO {

    @JSONField(name = "id")
    private String id;

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
    @JSONField(name = "openId")
    private String openId;

    //此条评论的创建时间
    @JSONField(name = "discussDate")
    private String discussDate;

    //此条评论的创建时间
    @JSONField(name = "discussContent")
    private String discussContent;


    @JSONField(name = "activityDiscussReplyDTOList")
    private List<ActivityDiscussReplyDTO> activityDiscussReplyDTOList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDiscussDate() {
        return discussDate;
    }

    public void setDiscussDate(String discussDate) {
        this.discussDate = discussDate;
    }

    public String getDiscussContent() {
        return discussContent;
    }

    public void setDiscussContent(String discussContent) {
        this.discussContent = discussContent;
    }

    public List<ActivityDiscussReplyDTO> getActivityDiscussReplyDTOList() {
        return activityDiscussReplyDTOList;
    }

    public void setActivityDiscussReplyDTOList(List<ActivityDiscussReplyDTO> activityDiscussReplyDTOList) {
        this.activityDiscussReplyDTOList = activityDiscussReplyDTOList;
    }
}