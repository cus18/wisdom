package com.wisdom.living.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.living.entity.LivingService;
import com.wisdom.living.entity.LivingServiceOffice;
import com.wisdom.living.entity.LivingServiceOrder;
import com.wisdom.living.service.LivingServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 居家服务板块
 * @author frank
 * @date 2015-10-14
 */

@Controller
@RequestMapping(value = "index")
public class LivingIndexController {

	@Autowired
	LivingServiceService livingServiceService;


	/**
	 * 获取 living service 列表
	 *
	 */
	@RequestMapping(value = "livingServiceList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO livingServiceList(@RequestBody LivingService livingService) {
		ResponseDTO responseDto=new ResponseDTO<>();
		responseDto.setResponseData(livingServiceService.getLivingService(livingService));
		responseDto.setResult(StatusConstant.SUCCESS);
		return responseDto;
	}


	/**
	 * 下订单
	 *
	 */
	@RequestMapping(value = "commitOrder", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO commitOrder(@RequestBody LivingServiceOrder livingServiceOrder) {
		ResponseDTO responseDto=new ResponseDTO<>();
		responseDto.setResponseData(livingServiceService.insertLivingServiceOrder(livingServiceOrder));
		responseDto.setResult(StatusConstant.SUCCESS);
		return responseDto;
	}

	/**
	 * 获取机构列表
	 *
	 */
	@RequestMapping(value = "getLivingOfficeList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO getLivingOfficeList(@RequestBody LivingServiceOffice livingServiceOffice) {
		ResponseDTO responseDto=new ResponseDTO<>();
		responseDto.setResponseData(livingServiceService.getLivingServiceOffice(livingServiceOffice));
		responseDto.setResult(StatusConstant.SUCCESS);
		return responseDto;
	}

	/**
	 * 获取机构列表
	 *
	 */
	@RequestMapping(value = "getLivingServiceOrderStatus", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO getLivingServiceOrderStatus(@RequestParam String openID,@RequestParam String status,HttpServletRequest request) {
		ResponseDTO responseDto=new ResponseDTO<>();
		responseDto.setResponseData(livingServiceService.getLivingServiceOrderStatus(openID,status));
		responseDto.setResult(StatusConstant.SUCCESS);
		return responseDto;
	}

	/**
	 * 催审
	 *
	 */
	@RequestMapping(value = "sendMessage", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO sendMessage(@RequestParam String livingServiceOrderID,HttpServletRequest request) {
		ResponseDTO responseDto=new ResponseDTO<>();
		livingServiceService.sendMessage(livingServiceOrderID,"");
		responseDto.setResult(StatusConstant.SUCCESS);
		responseDto.setResponseData("发送成功");
		return responseDto;
	}

	/**
	 * 催审
	 *
	 */
	@RequestMapping(value = "delLivingServiceOrder", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody

	ResponseDTO delLivingServiceOrder(@RequestParam String livingServiceOrderID) {
		ResponseDTO responseDto=new ResponseDTO<>();
		Integer a=livingServiceService.delLivingServiceOrder(livingServiceOrderID);
		responseDto.setResult(StatusConstant.SUCCESS);
		return responseDto;
	}


}
