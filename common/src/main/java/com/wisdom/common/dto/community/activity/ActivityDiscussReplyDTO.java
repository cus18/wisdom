package com.wisdom.common.dto.community.activity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by zbm84 on 2018/2/9.
 */
public class ActivityDiscussReplyDTO {

    //此条回复的对应的活动ID
    @JSONField(name = "activityDiscussID")
    private String activityDiscussID;

    //此条回复的评论者的logo
    @JSONField(name = "weChatHeadPhoto")
    private String weChatHeadPhoto;

    //此条回复的评论者的名称
    @JSONField(name = "weChatNickName")
    private String wechatNickName;

    //此条回复的评论者的ID号
    @JSONField(name = "openID")
    private String openID;

    //此条回复的创建时间
    @JSONField(name = "replyDate")
    private Date replyDate;

    //此条回复的内容
    @JSONField(name = "replyContent")
    private String replyContent;


    public String getActivityDiscussID() {
        return activityDiscussID;
    }

    public void setActivityDiscussID(String activityDiscussID) {
        this.activityDiscussID = activityDiscussID;
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

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}
