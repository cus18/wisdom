package com.wisdom.translation.service;

import com.wisdom.common.dto.community.activity.ActivityDTO;
import com.wisdom.common.dto.community.activity.ActivityDiscussDTO;
import com.wisdom.translation.mapper.ActivityDiscussMapper;
import com.wisdom.translation.mapper.ActivityMapper;
import com.wisdom.translation.mapper.ActivityUserMapper;
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

    public List<ActivityDTO> activityListByFirstPage(String hospitalID) {
        List<ActivityDTO> list = activityMapper.getMyHospitalActivityListByHospitalID(hospitalID);
        if(list.size()==0){
            list = activityMapper.getMyActivityListByElderID(null,2,null);
        }else if(list.size()==1){
            ActivityDTO activityDTO = activityMapper.getMyActivityListByElderID(null,2,null).get(1);
            list.add(activityDTO);
        }
        return list;
    }

    public List<ActivityDTO> getActivityList(String elderID, String pageNo,String activityType) {
        List<ActivityDTO> result = activityMapper.getMyActivityListByElderID(elderID,Integer.parseInt(pageNo)*10,activityType);
        return result;
    }

    public ActivityDTO getActivity(String elderID) {
        return activityMapper.getActivityList(elderID,null).get(0);
    }

    public Integer getActivityAttendStatus(String activityID, String elderID) {
        return activityUserMapper.getActivityCountByID(activityID, elderID);
    }

    public List<ActivityDiscussDTO> getActivityDiscuss(String id, Integer page) {
        return activityDiscussMapper.getActivityDiscussList(id,page);
    }
}
