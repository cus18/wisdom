package com.wisdom.core.controller.web;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.common.dto.core.user.LoginDTO;
import com.wisdom.common.dto.core.user.UserInfoDTO;
import com.wisdom.core.interceptor.LoginRequired;
import com.wisdom.core.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class LoginController {

	@Autowired
	LoginService loginService;

	/**
	 * 发送验证码
	 */
	@RequestMapping(value = "sendIdentifying", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<UserInfoDTO> sendIdentifying(HttpServletRequest request,
											 @RequestBody UserInfoDTO userInfoDTO) {
		ResponseDTO<UserInfoDTO> result = new ResponseDTO<>();
		result.setResult(loginService.sendMessage(userInfoDTO.getMobile()));
		return result;
	}

	/**
	 * 登录实现
	 */
	@RequestMapping(value = "login", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<LoginDTO> login(@RequestBody HashMap<String,Object> loginInfo, HttpServletRequest request) throws Exception {
		LoginDTO loginDto = loginService.login(loginInfo.get("phoneNum").toString(),
				loginInfo.get("identifyNum").toString(),
				loginInfo.get("source").toString(),
				request.getRemoteAddr().toString().toString(),
				request);
		ResponseDTO<LoginDTO> result = new ResponseDTO<>();
		if (loginDto == null) {
			result.setResult(StatusConstant.FAILURE);
			result.setErrorInfo("验证码输入不正确");
			return result;
		} else if (loginDto.getLoginToken().equals("00000")) {
			result.setResult(StatusConstant.LOGIN_ERROR);
			result.setErrorInfo("用户不存在");
			result.setResponseData(loginDto);
			return result;
		} else {
			result.setResult(StatusConstant.SUCCESS);
			result.setErrorInfo("调用成功");
			result.setResponseData(loginDto);
			return result;
		}
	}

	/**
	 * 退出登录
	 */
	@RequestMapping(value = "loginout", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<UserInfoDTO> loginout(HttpServletRequest request) {
		String loginToken=request.getHeader("logintoken");
		if(loginToken==null||loginToken.equals("")){
			loginToken=request.getSession().getAttribute("token").toString();
		}
		String status = loginService.loginOut(loginToken);
		ResponseDTO<UserInfoDTO> result = new ResponseDTO<UserInfoDTO>();
		result.setResult(status);
		result.setErrorInfo(status.equals(StatusConstant.LOGIN_OUT) ? "退出登录" : "保持在线");
		return result;
	}

}
