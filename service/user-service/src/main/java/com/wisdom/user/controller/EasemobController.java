package com.wisdom.user.controller;

import com.wisdom.common.dto.userService.EasemobGroupDTO;
import com.wisdom.user.service.EasemobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EasemobController {

	@Autowired
	EasemobService easemobService;

	@RequestMapping(value = "/sendEasemobMessage",method = {RequestMethod.POST, RequestMethod.GET})
	void sendEasemobMessage(@RequestParam String easemobGroup, @RequestParam String message)
	{
		easemobService.sendEasemobMessage(easemobGroup,message);
	}

	@RequestMapping(value = "/getEasemobMessageUrl",method = {RequestMethod.POST, RequestMethod.GET})
	String getEasemobMessageUrl(@RequestParam String... params)
	{
		return easemobService.getEasemobMessageUrl(params);
	}

	@RequestMapping(value = "/getEasemobGroup",method = {RequestMethod.POST, RequestMethod.GET})
	EasemobGroupDTO getEasemobGroup(@RequestParam String elderId)
	{
		return easemobService.getEasemobGroup(elderId);
	}

}
