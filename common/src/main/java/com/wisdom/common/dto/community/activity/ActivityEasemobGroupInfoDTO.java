package com.wisdom.common.dto.community.activity;

import java.util.List;

/**
 * Created by zbm84 on 2017/8/22.
 */
public class ActivityEasemobGroupInfoDTO {

    private List<ActivityEasemobGroupUserDTO> activityEasemobGroupUserDTO;
    private String activityID;

    public List<ActivityEasemobGroupUserDTO> getActivityEasemobGroupUserDTO() {
        return activityEasemobGroupUserDTO;
    }

    public void setActivityEasemobGroupUserDTO(List<ActivityEasemobGroupUserDTO> activityEasemobGroupUserDTO) {
        this.activityEasemobGroupUserDTO = activityEasemobGroupUserDTO;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }
}
