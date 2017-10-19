package com.wisdom.health.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.common.dto.health.*;
import com.wisdom.common.dto.core.user.UserInfoDTO;
import com.wisdom.health.client.CoreServiceClient;
import com.wisdom.health.interceptor.LoginRequired;
import com.wisdom.health.service.HealthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 检测与诊疗模块
 * @author frank
 * @date 2015-10-14
 */
@Controller
@RequestMapping(value = "detectionDiagnose")
public class DetectionDiagnosisController {

	@Autowired
	HealthDataService healthDataService;

	@Autowired
	CoreServiceClient CoreServiceClient;

	/**
	 * 获取当前用户的远程检测数据
	 *
	 *  input detectionDTO,detectionStartTime,detectionEndTime,elderId
	 *
	 *  output ResponseDTO<DetectionDTO>
	 *
	 */
	@RequestMapping(value = "detection", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<DetectionDTO> detection(@RequestParam String detectionType,
										@RequestParam String detectionDateType,
										@RequestParam String elderId) {
		ResponseDTO<DetectionDTO> responseDTO = healthDataService.getHealthDataFromMongo(detectionType,detectionDateType,elderId);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 给当前用户录入的新的检测数据
	 *
	 *  input DetectionDTO
	 *
	 *  output ResponseDTO<DetectionDTO>
	 *
	 */
	@RequestMapping(value = "createDetection", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<DetectionDTO> createDetection(@RequestBody DetectionDTO<CommonDataDTO> detectionDTO) {
		ResponseDTO<DetectionDTO> responseDTO = new ResponseDTO<DetectionDTO>();
		try {
			healthDataService.saveHealthDataToMongo(detectionDTO);
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
			responseDTO.setErrorInfo("测量时间不能晚于当前时间！");
		}
		return responseDTO;
	}

	/**
	 * 给当前用户录入的新的检测数据
	 *
	 *  input DetectionDTO
	 *
	 *  output ResponseDTO<DetectionDTO>
	 *
	 */
	@RequestMapping(value = "controlTarget", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<DetectionDTO> controlTarget(@RequestBody DetectionDTO<ControlTargetDataDTO> detectionDTO) {

		ResponseDTO<DetectionDTO> responseDTO = new ResponseDTO<DetectionDTO>();
		healthDataService.saveControlTargetToMongo(detectionDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 给当前用户录入的新的检测数据
	 *
	 *  input DetectionDTO
	 *
	 *  output ResponseDTO<DetectionDTO>
	 *
	 */
	@RequestMapping(value = "getControlTarget", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<DetectionDTO> getControlTarget(@RequestParam String elderId,
											   @RequestParam String detectionType) {

		ResponseDTO<DetectionDTO> responseDTO = new ResponseDTO<DetectionDTO>();
		DetectionDTO detectionDTO = healthDataService.getControlTargetFromMongo(elderId,detectionType);
		responseDTO.setResponseData(detectionDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 *  获取当前用户的化验报告
	 *
	 *  input testReportStartDate,testReportEndDate,elderId
	 *
	 *  output ResponseDTO<List<TestReportDTO>>
	 *
	 */
	@RequestMapping(value = "testReport", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List> testReport(@RequestParam String startDate,
								 @RequestParam String endDate,
								 @RequestParam String elderId) {
		
		ResponseDTO<List> responseDTO = healthDataService.getTestReport(elderId, startDate, endDate);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 *  创建用户的化验报告
	 *
	 *  input TestReportDTO,elderId
	 *
	 *  output ResponseDTO<List<TestReportDTO>>
	 *
	 */
	@RequestMapping(value = "createTestReport", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<TestReportDTO> createTestReport(@RequestBody TestReportDTO testReportDTO, HttpServletRequest request) {

		ResponseDTO<TestReportDTO> responseDTO = new ResponseDTO<TestReportDTO>();
		try {
			UserInfoDTO userInfoDTO = CoreServiceClient.getUserInfo(request);
			testReportDTO.setProviderId(userInfoDTO.getPractitionerUserDTO().getId());
			testReportDTO.setProviderName(userInfoDTO.getName());
			healthDataService.createTestReport(testReportDTO);
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		return responseDTO;
	}

	/**
	 *  获取当前用户某个时段的诊疗记录
	 *
	 *  input treatmentStartDate,treatmentEndDate,elderId
	 *
	 *  output ResponseDTO<List<TestReportDTO>>
	 *
	 */
	@RequestMapping(value = "treatment", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List> treatment(@RequestParam String startDate,
								@RequestParam String endDate,
								@RequestParam String elderId) {

		ResponseDTO<List> responseDTO = healthDataService.getTreatmentRecord(elderId, startDate, endDate);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 *  为当前用户创建新的诊疗记录
	 *
	 *  input TreatmentDTO,elderId
	 *
	 *  output ResponseDTO<List<TestReportDTO>>
	 *
	 */
	@RequestMapping(value = "createTreatment", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<TreatmentDTO> createTreatment(@RequestBody TreatmentDTO treatmentDTO, HttpServletRequest request) {

		ResponseDTO<TreatmentDTO> responseDTO = new ResponseDTO<TreatmentDTO>();
		try {
			UserInfoDTO userInfoDTO = CoreServiceClient.getUserInfo(request);
			treatmentDTO.setProviderId(userInfoDTO.getPractitionerUserDTO().getId());
			treatmentDTO.setProviderName(userInfoDTO.getName());
			healthDataService.createTreatment(treatmentDTO);
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		return responseDTO;
	}

}
