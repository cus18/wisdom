package com.wisdom.health.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.PageParamDTO;
import com.wisdom.common.dto.ResponseDTO;
import com.wisdom.common.dto.healthService.*;
import com.wisdom.common.dto.userService.UserInfoDTO;
import com.wisdom.health.client.UserServiceClient;
import com.wisdom.health.interceptor.LoginRequired;
import com.wisdom.health.service.HealthArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "healthArchive")
public class HealthArchiveController {

	@Autowired
	private HealthArchiveService healthArchiveService;

	@Autowired
	private UserServiceClient userServiceClient;

	@RequestMapping(value = "basicInfo", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<BasicInfoDTO> healthArchiveBasicInfo(@RequestParam String elderId) {
		ResponseDTO<BasicInfoDTO> responseDto = new ResponseDTO<>();
		try{
			responseDto.setResponseData(healthArchiveService.getElderBasicInfo(elderId));
			responseDto.setResult(StatusConstant.SUCCESS);
		}
		catch (Exception e)
		{
			responseDto.setErrorInfo("getHealthArchiveBascInfo failure");
			responseDto.setResult(StatusConstant.FAILURE);
		}
		return responseDto;
	}

	@RequestMapping(value = "physicalExaminationList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<PhysicalExaminationDTO>> healthArchivePhysicalExaminationList(@RequestBody PageParamDTO<MemberDTO> pageParamDto) {
		ResponseDTO<List<PhysicalExaminationDTO>> responseDto = new ResponseDTO<>();
		try{
			responseDto.setResponseData(healthArchiveService.getPhysicalExaminationList(pageParamDto));
			responseDto.setResult(StatusConstant.SUCCESS);
		}
		catch (Exception e)
		{
			responseDto.setErrorInfo("getHealthArchivePhysicalExamination failure");
			responseDto.setResult(StatusConstant.FAILURE);
		}
		return responseDto;
	}

	@RequestMapping(value = "physicalExaminationTemplateList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<PhysicalExaminationTemplateDTO>> healthArchivePhysicalExaminationTemplateList(@RequestBody PageParamDTO pageParamDto) {
		ResponseDTO<List<PhysicalExaminationTemplateDTO>> responseDto = new ResponseDTO<>();
		try{
			responseDto.setResponseData(healthArchiveService.getPhysicalExaminationTemplateList(pageParamDto));
			responseDto.setResult(StatusConstant.SUCCESS);
		}
		catch (Exception e)
		{
			responseDto.setErrorInfo("getHealthArchivePhysicalExamination failure");
			responseDto.setResult(StatusConstant.FAILURE);
		}
		return responseDto;
	}

	/**
	 *  获取当前管理用户的某次体检记录详情
	 *
	 * 	input pageParamDto, requestData(elderId/physicalExaminationId)
	 *
	 *  output List<PhysicalExaminationDto>
	 *
	 */
	@RequestMapping(value = "physicalExamination", method = {RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PhysicalExaminationDTO> healthArchivePhysicalExamination(@RequestParam(required = true) String physicalExaminationId) {
		ResponseDTO<PhysicalExaminationDTO> responseDto = new ResponseDTO<>();
		try{
			responseDto.setResponseData(healthArchiveService.getPhysicalExamination(physicalExaminationId));
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
	 *  为当前管理的用户创建新的体检记录
	 *
	 * 	input PhysicalExaminationDto, elderId
	 *
	 *  output List<PhysicalExaminationDto>
	 *
	 */
	@RequestMapping(value = "physicalExamination/create", method = {RequestMethod.POST})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PhysicalExaminationDTO> createPhysicalExamination(@RequestBody PhysicalExaminationDTO physicalExaminationDTO,
																  HttpServletRequest request) {
		ResponseDTO<PhysicalExaminationDTO> responseDto = new ResponseDTO<>();
		try{
			UserInfoDTO userInfoDTO = userServiceClient.getUserInfo(request);
			physicalExaminationDTO.setProviderId(userInfoDTO.getPractitionerUserDTO().getId());
			physicalExaminationDTO.setProviderType(userInfoDTO.getPractitionerUserDTO().getTitle());
			physicalExaminationDTO.setProviderName(userInfoDTO.getName());
			responseDto.setResponseData(healthArchiveService.createPhysicalExamination(physicalExaminationDTO));
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
	 *  获取当前管理用户参加的健康评估信息列表
	 *
	 *	input pageParamDto, elderId
	 *
	 *  output String
	 *  JSON数据被转换成String类型
	 *
	 */
	@RequestMapping(value = "healthAssessmentList", method = {RequestMethod.POST})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<HealthAssessmentDTO>> healthAssessmentList(@RequestBody PageParamDTO<MemberDTO> pageParamDto) {

		ResponseDTO<List<HealthAssessmentDTO>> responseDto = new ResponseDTO<>();
		try{
			responseDto.setResponseData(healthArchiveService.getHealthAssessmentList(pageParamDto));
			responseDto.setResult(StatusConstant.SUCCESS);
		}
		catch (Exception e)
		{
			responseDto.setErrorInfo("getHealthAssessmentList failure");
			responseDto.setResult(StatusConstant.FAILURE);
		}
		return responseDto;
	}

	/**
	 *  获取当前管理用户的某次健康评估信息详情
	 *
	 *	input healthAssessmentId, elderId
	 *
	 *  output HealthAssessmentDto
	 *
	 */
	@RequestMapping(value = "saveHealthAssessmentAnswer", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<HealthAssessmentDTO> saveHealthAssessmentAnswer(@RequestBody HealthAssessmentDTO healthAssessmentAnswer,
																HttpServletRequest request) {

		ResponseDTO responseDto = new ResponseDTO<>();
		try{
			UserInfoDTO userInfoDTO = userServiceClient.getUserInfo(request);
			healthAssessmentAnswer.setProviderId(userInfoDTO.getPractitionerUserDTO().getId());
			healthAssessmentAnswer.setProviderType(userInfoDTO.getPractitionerUserDTO().getTitle());
			healthAssessmentAnswer.setProviderName(userInfoDTO.getName());
			healthArchiveService.createHealthAssessment(healthAssessmentAnswer);
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
	 *  获取当前管理用户的某次健康评估信息详情
	 *
	 *	input healthAssessmentId, elderId
	 *
	 *  output HealthAssessmentDto
	 *
	 */
	@RequestMapping(value = "healthAssessment", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<HealthAssessmentDTO> healthAssessment(@RequestParam(required = true) String healthAssessmentId,
													  @RequestParam(required = true) String keyId) {

		ResponseDTO<HealthAssessmentDTO> responseDto = new ResponseDTO<>();
		try{
			responseDto.setResponseData(healthArchiveService.getHealthAssessment(healthAssessmentId,keyId));
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
	 *  获取健康评估信息模板列表
	 *
	 *	input PageParamDto
	 *
	 *  output HealthAssessmentTemplateDto
	 *
	 */
	@RequestMapping(value = "templateList", method = {RequestMethod.POST})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<HealthAssessmentTemplateDTO>> healthAssessmentTemplateList(@RequestBody PageParamDTO pageParamDto) {

		ResponseDTO<List<HealthAssessmentTemplateDTO>> responseDto = new ResponseDTO<>();
		try{
			responseDto.setResponseData(healthArchiveService.GetHealthArchiveHealthAssessmentTemplateList(pageParamDto));
			responseDto.setResult(StatusConstant.SUCCESS);
		}
		catch (Exception e)
		{
			responseDto.setErrorInfo("GetHealthArchiveHealthAssessmentTemplateList failure");
			responseDto.setResult(StatusConstant.FAILURE);
		}
		return responseDto;
	}
}
