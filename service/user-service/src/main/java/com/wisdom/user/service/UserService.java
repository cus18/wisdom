package com.wisdom.user.service;

import com.wisdom.common.dto.UserInfoDTO;
import com.wisdom.user.dto.PractitionerUserDTO;
import com.wisdom.user.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;

@Service
@Transactional(readOnly = false)
public class UserService {

    @Autowired
    RedisService redisService;

    /**
     * 获取当前登录的User
     *
     * @return
     */
    public UserInfoDTO getUserFromJedis(HttpServletRequest request) {
        String logintoken = request.getHeader("logintoken");
		if(logintoken==null||logintoken.equals("")){
			try {
				logintoken=request.getSession().getAttribute("token").toString();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
        String userInfo = redisService.get(logintoken);
        UserInfoDTO userInfoDTO = JSONUtils.toBean(userInfo, UserInfoDTO.class);
        return userInfoDTO;
    }


    public boolean updateUser(UserInfoDTO userInfo) {
        return true;
    }


    public void updateRedisUser(HttpServletRequest request) {
    }

    public PractitionerUserDTO getPractitionerUserInfo(String id) {

        return null;
    }

    public static UserInfoDTO getUser(HttpServletRequest request) {
        return  null;
    }
}
