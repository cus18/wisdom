package com.wisdom.core.service;

import com.google.gson.Gson;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.common.dto.core.user.RelativeElderDTO;
import com.wisdom.common.dto.core.user.UserInfoDTO;
import com.wisdom.core.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class UserService {

    @Autowired
    RedisService redisService;

    @Autowired
    UserMapper userMapper;

    /**
     * 获取当前登录的User
     *
     * @return
     */
    public UserInfoDTO getUserFromRedis(HttpServletRequest request) {
//        String loginToken = request.getHeader("loginToken");
        String loginToken = "e87080120043443db2764d052090c1a4elder";
        if (loginToken == null || loginToken.equals("")) {
            try {
                loginToken = request.getSession().getAttribute("token").toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        String userInfo = redisService.get(loginToken);
        UserInfoDTO userInfoDTO = new Gson().fromJson(userInfo, UserInfoDTO.class);
        return userInfoDTO;
    }

    public UserInfoDTO getUserFromLoginToken(String loginToken) {
        String userInfo = redisService.get(loginToken);
        UserInfoDTO userInfoDTO = new Gson().fromJson(userInfo, UserInfoDTO.class);
        return userInfoDTO;
    }

    public List<RelativeElderDTO> getRelativeList(UserInfoDTO userInfoDTO) {
        List<RelativeElderDTO> l = new ArrayList<>();
        RelativeElderDTO relativeElderDTO = new RelativeElderDTO();
        relativeElderDTO.setGender(userInfoDTO.getGender());
        relativeElderDTO.setAge(userInfoDTO.getAge());
        relativeElderDTO.setElderID(userInfoDTO.getElderUserDTO().getId());
        relativeElderDTO.setElderName(userInfoDTO.getName());
        relativeElderDTO.setEasemobID(userInfoDTO.getElderUserDTO().getEasemobID());
        relativeElderDTO.setEasemobPassword(userInfoDTO.getElderUserDTO().getEasemobPassword());
        l.add(relativeElderDTO);
        return l;
    }

    public String loginOut(String loginToken) {
        redisService.expire(loginToken, 0);
        return StatusConstant.LOGIN_OUT;
    }

    public ResponseDTO bindLaoyouUser(String phoneNum, String openid) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setLoginName(phoneNum);
        userInfoDTO = userMapper.getByLoginName(userInfoDTO);
        userInfoDTO.setOpenid(openid);
        ResponseDTO responseDTO = new ResponseDTO();
        userMapper.deleteUserOpenIdInfo(userInfoDTO);
        if (userMapper.updateUserOpenIdInfo(userInfoDTO) > 0) {
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setErrorInfo("绑定成功");
            responseDTO.setResponseData(userInfoDTO);
            return responseDTO;
        } else {
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setErrorInfo("手机号不存在");
            return responseDTO;
        }
    }


    public ResponseDTO getLaoyouUserByOpenId(String openid) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setOpenid(openid);
        userInfoDTO = userMapper.getByOpenId(userInfoDTO);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(userInfoDTO);
        return responseDTO;
    }

    public void deleteUserOpenIdInfo(String openid) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setOpenid(openid);
        userMapper.deleteUserOpenIdInfo(userInfoDTO);
    }
}
