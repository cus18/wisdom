package com.wisdom.medical.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RouteController {

	/*
        medical ionic框架
    */
	@RequestMapping(value ="ionic",method = {RequestMethod.POST, RequestMethod.GET})
	public String medicalIonic(HttpServletResponse response) {
		response.addHeader("Pragma","no-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Expires","0");
		return "angular/medicalIonicIndex";
	}

	/***
	 * *原生登陆页
	***/
	@RequestMapping(value ="login",method = {RequestMethod.POST, RequestMethod.GET})
	public String medicalLogin(HttpServletResponse response) {
		response.addHeader("Pragma","no-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Expires","0");
		return "native/login";
	}


}
