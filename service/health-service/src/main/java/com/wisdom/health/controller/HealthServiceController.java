package com.wisdom.health.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.PageParamDTO;
import com.wisdom.common.dto.ResponseDTO;
import com.wisdom.common.dto.healthService.HealthServicePackageDTO;
import com.wisdom.common.dto.healthService.HealthServicePackageTemplateDTO;
import com.wisdom.common.dto.healthService.MemberDTO;
import com.wisdom.health.interceptor.LoginRequired;
import com.wisdom.health.service.HealthPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 关于账户管理
 * @author frank
 * @date 2015-10-14
 */
@Controller
@RequestMapping(value = "healthService")
public class HealthServiceController {

	@Autowired
	private HealthPackageService healthPackageService;

	/**
	 * 获取当前用户正在进行的服务套餐列表
	 *
	 *  input PageParamDto
	 *
	 *  output List<HealthServicePackageDto>
	 *
	 */
	@RequestMapping(value = "packageList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<HealthServicePackageDTO>> healthPackageServiceList(@RequestBody PageParamDTO<MemberDTO> pageParamDto) {

		ResponseDTO<List<HealthServicePackageDTO>> responseDto = new ResponseDTO<>();
		try{
			responseDto.setResponseData(healthPackageService.getHealthServicePackageList(pageParamDto));
			responseDto.setResult(StatusConstant.SUCCESS);
		}
		catch (Exception e)
		{
			responseDto.setErrorInfo(StatusConstant.PARAM_ERROR);
			responseDto.setResult(StatusConstant.FAILURE);
		}
		return responseDto;
	}

	/**
	 * 获取当前用户正在进行的某个服务套餐的详细信息
	 *
	 *  input PageParamDto
	 *
	 *  output List<HealthServicePackageDto>
	 *
	 */
	@RequestMapping(value = "package", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<HealthServicePackageDTO> healthPackageService(@RequestParam(required = true) String healthPackageServiceId,
                                                              @RequestParam(required = true) String elderId) {
		ResponseDTO<HealthServicePackageDTO> responseDto = new ResponseDTO<>();
		try{
			responseDto.setResponseData(healthPackageService.getOnGoingHealthServicePackage(healthPackageServiceId));
			responseDto.setResult(StatusConstant.SUCCESS);
		}
		catch (Exception e)
		{
			responseDto.setErrorInfo("getOnGoingHealthServicePackage is failure");
			responseDto.setResult(StatusConstant.FAILURE);
		}
		return responseDto;
	}

	/**
	 * 获取当前所有可为用户创建的服务套餐列表
	 *
	 *  input PageParamDto
	 *
	 *  output List<HealthServicePackageDto>
	 *
	 */
	@RequestMapping(value = "templateList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<HealthServicePackageTemplateDTO>> healthPackageServiceTemplateList(@RequestBody PageParamDTO pageParamDto) {

		ResponseDTO<List<HealthServicePackageTemplateDTO>> responseDto = new ResponseDTO<>();
		try{
			responseDto.setResponseData(healthPackageService.getHealthServicePackageTemplateList(pageParamDto));
			responseDto.setResult(StatusConstant.SUCCESS);
		}
		catch (Exception e)
		{
			responseDto.setErrorInfo("pageParamDto is not valid");
			responseDto.setResult(StatusConstant.FAILURE);
		}
		return responseDto;

	}

	/**
	 * 获取服务套餐模板的服务协议详情
	 *
	 *  input serviceTemplateId
	 *
	 *  output HealthServicePackageTemplateDto
	 *
	 */
	@RequestMapping(value = "template", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<HealthServicePackageTemplateDTO> healthPackageServiceTemplate(@RequestParam String healthPackageServiceTemplateId) {

		ResponseDTO<HealthServicePackageTemplateDTO> responseDto = new ResponseDTO<>();
		try{
			responseDto.setResponseData(healthPackageService.getHealthServicePackageTemplateDetail(healthPackageServiceTemplateId));
			responseDto.setResult(StatusConstant.SUCCESS);
		}
		catch (Exception e)
		{
			responseDto.setErrorInfo("getHealthServicePackageTemplateDetail failure");
			responseDto.setResult(StatusConstant.FAILURE);
		}
		return responseDto;
	}

	/**
	 * 为用户创建新的服务套餐
	 *
	 *  input serviceTemplateId
	 *
	 *  output HealthServicePackageTemplateDto
	 *
	 */
	@RequestMapping(value = "package/create", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<HealthServicePackageDTO> createHealthServicePackage(@RequestBody HealthServicePackageDTO healthPackageServiceDto) {

		ResponseDTO<HealthServicePackageDTO> responseDto = new ResponseDTO<>();
		try{
			responseDto.setResponseData(healthPackageService.createHealthServicePackage(healthPackageServiceDto));
			responseDto.setResult(StatusConstant.SUCCESS);
		}
		catch (Exception e)
		{
			responseDto.setErrorInfo("getHealthServicePackageTemplateDetail failure");
			responseDto.setResult(StatusConstant.FAILURE);
		}
		return responseDto;
	}


	@RequestMapping(value = "elderContactInfo", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<MemberDTO> elderContactInfo(@RequestParam(required = true) String elderId) {

		ResponseDTO<MemberDTO> responseDto = new ResponseDTO<>();
		try{
			responseDto.setResponseData(healthPackageService.getElderContactInfo(elderId));
			responseDto.setResult(StatusConstant.SUCCESS);
		}
		catch (Exception e)
		{
			responseDto.setErrorInfo("getElderContactInfo failure");
			responseDto.setResult(StatusConstant.FAILURE);
		}
		return responseDto;
	}

}
