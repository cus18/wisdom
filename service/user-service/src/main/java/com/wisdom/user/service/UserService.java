package com.wisdom.user.service;

import com.google.gson.Gson;
import com.wisdom.common.dto.userService.RelativeElderDTO;
import com.wisdom.common.dto.userService.UserInfoDTO;
import com.wisdom.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        String loginToken = request.getHeader("loginToken");
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

    public List<RelativeElderDTO> getRelativeList(UserInfoDTO userInfoDTO)
    {
        List<RelativeElderDTO> l=new ArrayList<>();
        RelativeElderDTO relativeElderDTO = new RelativeElderDTO();
        relativeElderDTO.setGender(userInfoDTO.getGender());
        relativeElderDTO.setAge(userInfoDTO.getAge());
        relativeElderDTO.setElderID(userInfoDTO.getElderUserDTO().getId());
        relativeElderDTO.setElderName(userInfoDTO.getName());
        l.add(relativeElderDTO);
        return l;
    }

}
