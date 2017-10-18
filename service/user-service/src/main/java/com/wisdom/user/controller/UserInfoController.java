package com.wisdom.user.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.ResponseDTO;
import com.wisdom.common.dto.userService.ElderUserDTO;
import com.wisdom.common.dto.userService.RelativeElderDTO;
import com.wisdom.user.service.RedisService;
import com.wisdom.user.client.HealthServiceClient;
import com.wisdom.user.interceptor.*;
import com.wisdom.user.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserInfoController {


	@Autowired
	HealthServiceClient healthServiceClient;

	@Autowired
	UserService userService;

	@Autowired
	PractitionerUserService practitionerUserService;

	@Autowired
	RedisService redisService;

	/**
	 * 获取用户所有的亲友圈中亲友的信息
	 * @return
	 */
	@RequestMapping(value = "relativeElderInfo", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<RelativeElderDTO>> relativeElderInfo(HttpServletRequest request) {

		ResponseDTO<List<RelativeElderDTO>> responseDTO = new ResponseDTO<>();

		/**
		 * 获取用户所有的亲友圈中亲友的信息，将用户的亲友圈的亲友信息放入RelativeElderDTO中
		 * */
		responseDTO.setResponseData(userService.getRelativeList(userService.getUserFromRedis(request)));
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 获取登录用户的基础信息
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<ElderUserDTO> getUserInfo(HttpServletRequest request) {
		ResponseDTO responseDto = new ResponseDTO();
//		UserInfoDTO userInfoDTO = userService.getUserFromJedis(request);
//		responseDto.setResponseData(userInfoDTO);
		//responseDto.setResult(StatusConstant.SUCCESS);
		return responseDto;
	}


}
