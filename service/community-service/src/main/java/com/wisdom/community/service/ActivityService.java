package com.wisdom.community.service;

import com.wisdom.common.dto.basic.ActivityDiscuss;
import com.wisdom.common.dto.basic.ActivityEasemobGroup;
import com.wisdom.common.dto.basic.ActivityUser;
import com.wisdom.common.dto.basic.WeChatUserInfo;
import com.wisdom.common.dto.community.activity.ActivityDTO;
import com.wisdom.common.dto.community.activity.ActivityDiscussDTO;
import com.wisdom.common.dto.community.activity.ActivityDiscussReplyDTO;
import com.wisdom.community.client.CoreServiceClient;
import com.wisdom.community.client.WeChatServiceClient;
import com.wisdom.community.mapper.ActivityDiscussMapper;
import com.wisdom.community.mapper.ActivityEasemobGroupMapper;
import com.wisdom.community.mapper.ActivityMapper;
import com.wisdom.community.mapper.ActivityUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zbm84 on 2017/7/24.
 */
@Service
@Transactional(readOnly = false)
public class ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityUserMapper activityUserMapper;

    @Autowired
    private ActivityDiscussMapper activityDiscussMapper;

    @Autowired
    private ActivityEasemobGroupMapper activityEasemobGroupMapper;

    @Autowired
    CoreServiceClient coreServiceClient;

    @Autowired
    WeChatServiceClient weChatServiceClient;

    public List<ActivityDTO> activityListByFirstPage() {
        List<ActivityDTO> list = activityMapper.getMyHospitalActivityListByHospitalID();
        if(list.size()==0){
            list = activityMapper.getMyActivityListByElderID(null,2,null);
        }else if(list.size()==1){
            ActivityDTO activityDTO = activityMapper.getMyActivityListByElderID(null,2,null).get(1);
            list.add(activityDTO);
        }
        return list;
    }

    public List<ActivityDTO> getActivityList(String openid, String pageNo,String activityType) {
        List<ActivityDTO> result = activityMapper.getMyActivityListByElderID(openid,Integer.parseInt(pageNo)*10,activityType);
        return result;
    }

    public ActivityDTO getActivity(String elderID) {
        return activityMapper.getActivityList(elderID,null).get(0);
    }

    public Integer getActivityAttendStatus(String activityID, String openID) {
        return activityUserMapper.getActivityCountByID(activityID, openID);
    }

    public List<ActivityDiscussDTO> getActivityDiscuss(String id, Integer page) {
        List<ActivityDiscussDTO> activityDiscussDTOList=activityDiscussMapper.getActivityDiscussList(id,page);
        for (ActivityDiscussDTO a:activityDiscussDTOList) {
            WeChatUserInfo weChatUserInfo=weChatServiceClient.getWechatUserInfo(a.getOpenId());
            a.setWeChatHeadPhoto(weChatUserInfo.getHeadimgurl());
            a.setWechatNickName(weChatUserInfo.getNickname());
            List<ActivityDiscussReplyDTO> activityDiscussReplyDTO=activityDiscussMapper.getActivityDiscussReplyList(a.getId());
            for (ActivityDiscussReplyDTO adr:activityDiscussReplyDTO) {
                WeChatUserInfo weChatUserInfos=weChatServiceClient.getWechatUserInfo(a.getOpenId());
                adr.setWeChatHeadPhoto(weChatUserInfos.getHeadimgurl());
                adr.setWechatNickName(weChatUserInfos.getNickname());
            }
            a.setActivityDiscussReplyDTOList(activityDiscussReplyDTO);
        }
        return activityDiscussDTOList;
    }


    @Transactional(rollbackFor = Exception.class)
    public String addActivityUser(String activityID,String openid) {
        ActivityUser activityUser = new ActivityUser();
        activityUser.setActivityID(activityID);
        ActivityDTO activity = activityMapper.getActivityList(activityID,null).get(0);
        Integer nums=activityUserMapper.getActivityCountByID(activityID, null);
        if(nums!=0&&nums.equals(activity.getPeopleNum())){
            return "max";
        }
        activityUser.setOpenID(openid);
        activityUserMapper.addActivityUser(activityUser);

//        boolean ifGroup=activity.getActivityEasemobGroupID()!=null && !activity.getActivityEasemobGroupID().equals("");
//        //将用户加入群组
//        if (ifGroup) {
//
//            coreServiceClient.signEasemobUser(openid,"123456");
//            boolean a = coreServiceClient.joinEasemobGroup(activity.getActivityEasemobGroupID(), openid);
//            if (a) {
//                ActivityEasemobGroup activityEasemobGroup = activityEasemobGroupMapper.searchActivityEasemobGroupByGroupID(activity.getActivityEasemobGroupID());
//                activityEasemobGroup.setMembers(activityEasemobGroup.getMembers().equals("") ? openid : activityEasemobGroup.getMembers() + "," + openid);
//                activityEasemobGroupMapper.updateActivityEasemobGroup(activityEasemobGroup);
//            }
//        }

        activity = activityMapper.getActivityList(activityID,null).get(0);
        return activity.getActivityEasemobGroupID();
    }


    public Integer addActivityDiscuss(ActivityDiscussDTO activityDiscussDTO) {
        ActivityDiscuss activityDiscuss = new ActivityDiscuss();
        activityDiscuss.setContent(activityDiscussDTO.getDiscussContent());
        activityDiscuss.setOpenID(activityDiscussDTO.getOpenId());
        activityDiscuss.setActivityID(activityDiscussDTO.getActivityId());
        return activityDiscussMapper.addActivityDiscuss(activityDiscuss);
    }

    public Integer addActivityDiscussReply(ActivityDiscussReplyDTO activityDiscussReplyDTO) {
        return activityDiscussMapper.addActivityDiscussReply(activityDiscussReplyDTO);
    }


    public List<ActivityEasemobGroup> getUserActivityGroupInfo(String elderEasemobID) {

        return activityEasemobGroupMapper.getUserActivityEasemobGroupList(elderEasemobID);
    }

    public String joinActivityEasemobGroup(String openid,String activityID) {
        coreServiceClient.signEasemobUser(openid,openid,weChatServiceClient.getWechatUserInfo(openid).getNickname());
//        coreServiceClient.signEasemobUser(openid,openid);
        ActivityDTO activity = activityMapper.getActivityList(activityID,null).get(0);
//        ActivityEasemobGroup activityEasemobGroup=activityEasemobGroupDao.searchActivityEasemobGroupByID(activity.getActivityEasemobGroupID());
        boolean a=coreServiceClient.joinEasemobGroup(activity.getActivityEasemobGroupID(),openid);
        if(a){
            ActivityEasemobGroup activityEasemobGroup=new ActivityEasemobGroup();
            activityEasemobGroup.setMembers(activityEasemobGroup.getMembers().equals("")?openid:activityEasemobGroup.getMembers()+","+openid);
            activityEasemobGroupMapper.updateActivityEasemobGroup(activityEasemobGroup);
        }
        return activity.getActivityEasemobGroupID();
    }

}
