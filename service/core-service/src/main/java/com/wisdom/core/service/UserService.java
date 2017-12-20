package com.wisdom.core.service;

import com.google.gson.Gson;
import com.wisdom.common.constant.StatusConstant;
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
    public UserInfoDTO getUserFromRedis(HttpServletRequest request)
    {
//        String loginToken = request.getHeader("loginToken");
        String loginToken="e87080120043443db2764d052090c1a4elder";
		if(loginToken==null||loginToken.equals("")){
			try {
				loginToken=request.getSession().getAttribute("token").toString();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
        String userInfo = redisService.get(loginToken);
        UserInfoDTO userInfoDTO = new Gson().fromJson(userInfo,UserInfoDTO.class);
        return userInfoDTO;
    }

    public UserInfoDTO getUserFromLoginToken(String loginToken)
    {
        String userInfo = redisService.get(loginToken);
        UserInfoDTO userInfoDTO = new Gson().fromJson(userInfo,UserInfoDTO.class);
        return userInfoDTO;
    }

    public List<RelativeElderDTO> getRelativeList(UserInfoDTO userInfoDTO)
    {
        List<RelativeElderDTO> l=new ArrayList<>();
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
        redisService.expire(loginToken,0);
        return StatusConstant.LOGIN_OUT;
    }

}
