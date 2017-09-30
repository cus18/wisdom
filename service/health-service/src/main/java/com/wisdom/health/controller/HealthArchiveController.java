package com.wisdom.health.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthArchiveController {

	/**
	 * 获取用户所有的亲友圈中亲友的信息
	 * @return
	 */
	@RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.GET})
	//@LoginRequired
	public
	@ResponseBody
	String test() {
		return "hello";
	}
}
