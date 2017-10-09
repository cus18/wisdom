package com.wisdom.user.service;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.util.DaHanTricomSMSMessageUtil;
import com.wisdom.user.mapper.DaHanTricomMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class LoginService {

    @Autowired
    DaHanTricomMessageMapper daHanTricomMessageMapper;

    public String sendMessage(String phoneNum) {
        try {
            String num = DaHanTricomSMSMessageUtil.sendIdentifying(phoneNum);
            daHanTricomMessageMapper.insertIdentifying(phoneNum, num);
            return StatusConstant.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return StatusConstant.FAILURE;
        }
    }

}
