package com.wisdom.community.service;

import com.alibaba.fastjson.JSON;
import com.wisdom.common.dto.community.activity.ActivityDTO;
import com.wisdom.community.mapper.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zbm84 on 2017/7/24.
 */
@Service
@Transactional(readOnly = false)
public class ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

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

}
