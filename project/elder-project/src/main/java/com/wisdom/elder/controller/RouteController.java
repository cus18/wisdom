package com.wisdom.elder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RouteController {

	/*
        elder ionic框架
    */
	@RequestMapping(value ="elder",method = {RequestMethod.POST, RequestMethod.GET})
	public String elderIonic(HttpServletResponse response) {
		response.addHeader("Pragma","no-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Expires","0");
		return "angular/elderIonicIndex";
	}

	/***
	 * *原生登陆页
	***/
	@RequestMapping(value ="login",method = {RequestMethod.POST, RequestMethod.GET})
	public String elderLogin(HttpServletResponse response) {
		response.addHeader("Pragma","no-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Expires","0");
		return "native/login";
	}


}
