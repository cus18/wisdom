package com.wisdom.user.service;

import com.wisdom.common.dto.userService.PractitionerUserDTO;
import com.wisdom.common.dto.userService.UserInfoDTO;
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

}
