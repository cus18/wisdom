package com.wisdom.health.controller;

import com.wisdom.common.dto.RelativeElderDTO;
import com.wisdom.common.dto.ResponseDTO;
import com.wisdom.common.dto.UserInfoDTO;
import com.wisdom.health.client.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HealthArchiveController {

	@Autowired
	UserServiceClient userServiceClient;

	/**
	 * 获取用户所有的亲友圈中亲友的信息
	 * @return
	 */
	@RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.GET})
	//@LoginRequired
	public
	@ResponseBody
	String test() {

		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setPhone("13121237551");

		ResponseDTO<List<RelativeElderDTO>> responseDTO = userServiceClient.relativeElderInfo(userInfoDTO);

		return "hello";
	}
}
