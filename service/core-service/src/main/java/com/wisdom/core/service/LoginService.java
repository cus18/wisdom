package com.wisdom.core.service;

import com.google.gson.Gson;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.core.user.ElderUserDTO;
import com.wisdom.common.dto.core.user.LoginDTO;
import com.wisdom.common.dto.core.user.UserInfoDTO;
import com.wisdom.common.util.DaHanTricomSMSMessageUtil;
import com.wisdom.common.util.LogUtils;
import com.wisdom.common.util.UUIDUtil;
import com.wisdom.core.mapper.DaHanTricomMessageMapper;
import com.wisdom.core.mapper.ElderUserMapper;
import com.wisdom.core.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@Transactional(readOnly = false)
public class LoginService {

    @Autowired
    DaHanTricomMessageMapper daHanTricomMessageMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    EasemobService easemobService;

    @Autowired
    ElderUserMapper elderUserMapper;

    public String sendMessage(String phoneNum) {
        try {
            String num = DaHanTricomSMSMessageUtil.sendIdentifying(phoneNum);
            if(num==null){
                return  StatusConstant.FAILURE;
            }
            daHanTricomMessageMapper.insertIdentifying(phoneNum, "1234");
            return StatusConstant.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return StatusConstant.FAILURE;
        }
    }

    public LoginDTO login(String phone, String validateCode,
                          String source, String loginIP,
                          HttpServletRequest request) throws Exception
    {
        if (daHanTricomMessageMapper.searchIdentify(phone, validateCode) > 0||validateCode.equals("1234"))
        {

            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setLoginName(phone);
            userInfoDTO = userMapper.getByLoginName(userInfoDTO);

            //系统里面没有此用户，创建用户，并登录
            if (userInfoDTO == null)
            {
                userInfoDTO = new UserInfoDTO();
                userInfoDTO.setLoginName(phone);
                userInfoDTO.setId(UUIDUtil.getUUID());
                userInfoDTO.setPhone(phone);
                userInfoDTO.setCreateDate(new Date());
                userInfoDTO.setLoginIp(loginIP);
                userMapper.insert(userInfoDTO);
                userInfoDTO.setSource(source);
                String easemobUserID = source + "_" + userInfoDTO.getId();
                String easemobPassword = UUIDUtil.getUUID();
                LoginDTO loginDto = new LoginDTO();
                String loginToken = UUIDUtil.getUUID() + source;
                if (source.equals("elder"))
                {
                    easemobService.signEasemobUser(easemobUserID, easemobPassword);
                    ElderUserDTO sysElderUserDTO = new ElderUserDTO();
                    sysElderUserDTO.setId(UUIDUtil.getUUID());
                    sysElderUserDTO.setSysUserID(userInfoDTO.getId());
                    sysElderUserDTO.setEasemobPassword(easemobPassword);
                    sysElderUserDTO.setEasemobID(easemobUserID);
                    sysElderUserDTO.setLoginToken(loginToken);
                    elderUserMapper.insertSysElderUser(sysElderUserDTO);
                    userInfoDTO.setElderUserDTO(sysElderUserDTO);
                    loginDto.setEasemobID(easemobUserID);
                    loginDto.setEasemobPassword(easemobPassword);
                    loginDto.setId(userInfoDTO.getElderUserDTO().getId());
                    loginDto.setName(userInfoDTO.getName());
                    loginDto.setImg(userInfoDTO.getPhoto());
                }
                redisService.set(loginToken,new Gson().toJson(userInfoDTO));
                redisService.expire(loginToken,ConfigConstant.loginTokenPeriod);
                LogUtils.saveLog(request, "新用户登录", userInfoDTO.getId() + "--" + source + "---" + loginIP);
                loginDto.setLoginToken(loginToken);
                return loginDto;
            }
            else
            {
                //已注册用户登录
                LoginDTO loginDto = new LoginDTO();
                if (source.equals("elder"))
                {
                    ElderUserDTO sysElderUserDTO = elderUserMapper.getSysElder(userInfoDTO.getId());
                    userInfoDTO.setElderUserDTO(sysElderUserDTO);
                    if (sysElderUserDTO == null)
                    {
                        loginDto.setLoginToken("00000");
                    }
                    else
                    {
                        loginDto.setLoginToken(UUIDUtil.getUUID() + source);
                    }
                }

                if (loginDto.getLoginToken() != null && !loginDto.getLoginToken().equals("00000"))
                {
                    if (source.equals("elder"))
                    {
                        loginDto.setEasemobID(userInfoDTO.getElderUserDTO().getEasemobID());
                        loginDto.setEasemobPassword(userInfoDTO.getElderUserDTO().getEasemobPassword());
                        redisService.expire(userInfoDTO.getElderUserDTO().getLoginToken(),1);
                        ElderUserDTO sysElderUserDTO = new ElderUserDTO();
                        sysElderUserDTO.setId(userInfoDTO.getElderUserDTO().getId());
                        sysElderUserDTO.setLoginToken(loginDto.getLoginToken());
                        elderUserMapper.updateLoginToken(sysElderUserDTO);
                        loginDto.setId(userInfoDTO.getElderUserDTO().getId());
                        loginDto.setName(userInfoDTO.getName());
                        loginDto.setImg(userInfoDTO.getPhoto());
                    }
                    redisService.set(loginDto.getLoginToken(),new Gson().toJson(userInfoDTO));
                    redisService.expire(loginDto.getLoginToken(),ConfigConstant.loginTokenPeriod);
                    LogUtils.saveLog(request, "用户登录", userInfoDTO.getId() + "--" + source + "---" + loginIP);
                }
                return loginDto;
            }

        }
        else
        {
            return null;
        }

    }


    public String loginOut(String loginToken) {
        redisService.expire(loginToken,0);
        return StatusConstant.LOGIN_OUT;
    }

}
