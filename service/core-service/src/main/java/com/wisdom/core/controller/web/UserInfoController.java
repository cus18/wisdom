package com.wisdom.core.controller.web;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.common.dto.core.user.ElderUserDTO;
import com.wisdom.common.dto.core.user.RelativeElderDTO;
import com.wisdom.common.dto.core.user.UserInfoDTO;
import com.wisdom.core.interceptor.LoginRequired;
import com.wisdom.core.mapper.DaHanTricomMessageMapper;
import com.wisdom.core.service.LoginService;
import com.wisdom.core.service.PractitionerUserService;
import com.wisdom.core.service.RedisService;
import com.wisdom.core.client.HealthServiceClient;
import com.wisdom.core.service.UserService;
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

	@Autowired
	DaHanTricomMessageMapper daHanTricomMessageMapper;

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
		UserInfoDTO userInfoDTO = userService.getUserFromRedis(request);
		responseDto.setResponseData(userInfoDTO);
		responseDto.setResult(StatusConstant.SUCCESS);
		return responseDto;
	}

	/**
	 * 退出登录
	 */
	@RequestMapping(value = "laoyou/loginout", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<UserInfoDTO> loginout(HttpServletRequest request) {
		String loginToken=request.getHeader("logintoken");
		if(loginToken==null||loginToken.equals("")){
			loginToken=request.getSession().getAttribute("token").toString();
		}
		String status = userService.loginOut(loginToken);
		ResponseDTO<UserInfoDTO> result = new ResponseDTO<>();
		result.setResult(status);
		result.setErrorInfo(status.equals(StatusConstant.LOGIN_OUT) ? "退出登录" : "保持在线");
		return result;
	}

	/**
	 * 与老友用户绑定
	 * @return
	 */
	@RequestMapping(value = "bindLaoyouUser", method = {RequestMethod.POST, RequestMethod.GET})
	public ResponseDTO bindLaoyouUser(@RequestParam String phone, @RequestParam String num, @RequestParam String openid) throws  Exception{
		ResponseDTO responseDTO=new ResponseDTO();
		if(daHanTricomMessageMapper.searchIdentify(phone,num)>0){
			return userService.bindLaoyouUser(phone,num);
		}else{
			responseDTO.setResult(StatusConstant.SUCCESS);
			responseDTO.setErrorInfo("验证码不正确");
			return responseDTO;
		}
	}



}
