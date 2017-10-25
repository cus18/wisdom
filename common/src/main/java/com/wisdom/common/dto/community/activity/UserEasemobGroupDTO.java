package com.wisdom.common.dto.community.activity;


import com.wisdom.common.dto.basic.ActivityEasemobGroup;
import com.wisdom.common.dto.basic.EasemobGroup;

import java.util.List;

/**
 * Created by zbm84 on 2017/8/16.
 */
public class UserEasemobGroupDTO {

    private EasemobGroup easemobGroup;

    private List<ActivityEasemobGroup> activityEasemobGroupInfoList;

    public EasemobGroup getEasemobGroup() {
        return easemobGroup;
    }

    public void setEasemobGroup(EasemobGroup easemobGroup) {
        this.easemobGroup = easemobGroup;
    }

    public List<ActivityEasemobGroup> getActivityEasemobGroupInfoList() {
        return activityEasemobGroupInfoList;
    }

    public void setActivityEasemobGroupInfoList(List<ActivityEasemobGroup> activityEasemobGroupInfoList) {
        this.activityEasemobGroupInfoList = activityEasemobGroupInfoList;
    }
}
