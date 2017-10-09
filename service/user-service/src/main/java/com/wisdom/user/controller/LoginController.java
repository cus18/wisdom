package com.wisdom.user.controller;


import com.wisdom.common.dto.ResponseDTO;
import com.wisdom.common.dto.UserInfoDTO;
import com.wisdom.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
public class LoginController {

	@Autowired
	LoginService loginService;

	/**
	 * 发送验证码
	 */
	@RequestMapping(value = "laoyou/sendIdentifying", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<UserInfoDTO> sendIdentifying(@RequestBody UserInfoDTO userInfoDto) {
		ResponseDTO<UserInfoDTO> result = new ResponseDTO<UserInfoDTO>();
		result.setResult(loginService.sendMessage(userInfoDto.getPhone()));
		return result;
	}

}
