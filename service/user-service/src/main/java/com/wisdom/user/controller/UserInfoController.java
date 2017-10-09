package com.wisdom.user.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.ElderInfoDTO;
import com.wisdom.common.dto.ResponseDTO;
import com.wisdom.common.dto.RelativeElderDTO;
import com.wisdom.common.dto.UserInfoDTO;
import com.wisdom.user.client.HealthServiceClient;
import com.wisdom.user.dto.PractitionerUserDTO;
import com.wisdom.user.dto.SurveyDTO;
import com.wisdom.user.interceptor.LoginRequired;
import com.wisdom.user.service.PractitionerUserService;
import com.wisdom.user.service.RedisService;
import com.wisdom.user.service.SurveyService;
import com.wisdom.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class UserInfoController {


	@Autowired
	HealthServiceClient healthServiceClient;

	@Autowired
	SurveyService surveyService;

	@Autowired
	UserService userService;

	@Autowired
	PractitionerUserService practitionerUserService;

	@Autowired
	RedisService redisService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Value("${test.value}")
	String value;

	/**
	 * 获取用户所有的亲友圈中亲友的信息
	 * @return
	 */
	@RequestMapping(value = "/relativeElderInfo", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<RelativeElderDTO>> relativeElderInfo(HttpServletRequest request) {

		System.out.println(StatusConstant.LOGIN_ERROR);
		System.out.println(value);

		ResponseDTO<List<RelativeElderDTO>> responseDTO = new ResponseDTO<List<RelativeElderDTO>>();

		/**
		 * 获取用户所有的亲友圈中亲友的信息，将用户的亲友圈的亲友信息放入RelativeElderDTO中
		 * */
		//List<RelativeElderDTO> elderInfoList = new ArrayList<RelativeElderDTO>();
		//responseDTO.setResponseData(healthArchiveService.getRelativeList(UserService.getUser(request)));
		//responseDTO.setResult(StatusConstant.SUCCESS);

//		List<RelativeElderDTO> elderInfoList = new ArrayList<RelativeElderDTO>();
		String test = healthServiceClient.healthServiceTest();
		surveyService.createData();
		surveyService.findData();
//
//
		Query query = new Query().with(new Sort(Sort.Direction.ASC, "questionId"));
		List<SurveyDTO> surveyDTOList = this.mongoTemplate.find(query, SurveyDTO.class,"surveys");
//
//
		boolean isOk = redisService.set("name", "陈佳科");
		String name = redisService.get("name");
		redisService.expire("name",1);
		name = redisService.get("name");

		return responseDTO;
	}

	/**
	 * 获取登录用户的基础信息
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<ElderInfoDTO> getUserInfo(HttpServletRequest request) {
		ResponseDTO responseDto = new ResponseDTO();
		UserInfoDTO userInfoDTO = userService.getUserFromJedis(request);
		responseDto.setResponseData(userInfoDTO);
		//responseDto.setResult(StatusConstant.SUCCESS);
		return responseDto;
	}


	/**
	 * 修改用户头像;
	 *
	 *
	 */
	@RequestMapping(value = "/updateHeadImage", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<UserInfoDTO> updateHeadImage(@RequestBody UserInfoDTO userInfoDto, HttpServletRequest request) {
		UserInfoDTO userInfo = userService.getUserFromJedis(request);
		userInfo.setPhoto(userInfoDto.getPhoto());
		ResponseDTO<UserInfoDTO> responseDto = new ResponseDTO<UserInfoDTO>();
		if (userService.updateUser(userInfo)){
			//responseDto.setResult(StatusConstant.SUCCESS);
			responseDto.setErrorInfo("");
			return responseDto;
		}else {
			//responseDto.setResult(StatusConstant.FAILURE);
			responseDto.setErrorInfo("");
			return responseDto;
		}
	}

	/**
	 * 修改用户姓名
	 *
	 *
	 */
	@RequestMapping(value = "/updateUserName", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<UserInfoDTO> updateUserName(@RequestBody UserInfoDTO userInfoDto, HttpServletRequest request)throws Exception {
		UserInfoDTO userInfo = userService.getUserFromJedis(request);
		userInfo.setId(userInfoDto.getId());
		userInfo.setName(userInfoDto.getName());
		if(userInfoDto.getAge()!=null&&!userInfoDto.getAge().equals("")){
			userInfo.setAge(userInfoDto.getAge());
		}
		if(userInfoDto.getGender()!=null&&!userInfoDto.getGender().equals("")){
			userInfo.setGender(userInfoDto.getGender());
		}
		ResponseDTO<UserInfoDTO> responseDto = new ResponseDTO<UserInfoDTO>();
		if (userService.updateUser(userInfo))
		{
			userService.updateRedisUser(request);
			//responseDto.setResult(StatusConstant.SUCCESS);
			responseDto.setErrorInfo("");
			return responseDto;

		}else {
			//responseDto.setResult(StatusConstant.FAILURE);
			responseDto.setErrorInfo("");
			return responseDto;
		}
	}


	/**
	 * 修改用户性别
	 *
	 *
	 */
	@RequestMapping(value = "/updateUserGender", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<UserInfoDTO> updateUserGender(@RequestBody UserInfoDTO userInfoDto, HttpServletRequest request) {
		UserInfoDTO userInfo = userService.getUserFromJedis(request);
		userInfo.setGender(userInfoDto.getGender());
		ResponseDTO<UserInfoDTO> responseDto=new ResponseDTO<UserInfoDTO>();
		if (userService.updateUser(userInfo)){
			userService.updateRedisUser(request);
			//responseDto.setResult(StatusConstant.SUCCESS);
			responseDto.setErrorInfo("");
			return responseDto;
		}else {
			//responseDto.setResult(StatusConstant.FAILURE);
			responseDto.setErrorInfo("");
			return responseDto;
		}
	}


	/**
	 * 修改用户地区
	 *
	 *
	 */
	@RequestMapping(value = "/updateUserArea", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<UserInfoDTO> updateUserArea(@RequestBody UserInfoDTO userInfoDto, HttpServletRequest request) {
		UserInfoDTO userInfo = userService.getUserFromJedis(request);
		userInfo.setArea(userInfoDto.getArea());
		ResponseDTO<UserInfoDTO> responseDto=new ResponseDTO<UserInfoDTO>();
		if (userService.updateUser(userInfo)){
			userService.updateRedisUser(request);
			//responseDto.setResult(StatusConstant.SUCCESS);
			responseDto.setErrorInfo("");
			return responseDto;
		}else {
			//responseDto.setResult(StatusConstant.FAILURE);
			responseDto.setErrorInfo("");
			return responseDto;
		}
	}

	/**
	 * 修改用户科室
	 *
	 *
	 */
	@RequestMapping(value = "/updatePractitionerUserDepartment", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PractitionerUserDTO> updateUserDepartment(@RequestBody PractitionerUserDTO practitionerUserDTO,
												  HttpServletRequest request) {
		UserInfoDTO userInfo = userService.getUserFromJedis(request);
		PractitionerUserDTO practitionerUserInfo = userService.getPractitionerUserInfo(userInfo.getId());
		practitionerUserInfo.setDepartment(practitionerUserDTO.getDepartment());
		ResponseDTO<PractitionerUserDTO> responseDto=new ResponseDTO<PractitionerUserDTO>();
		if (practitionerUserService.updateSysPractitionerUser(practitionerUserInfo)){
			userService.updateRedisUser(request);
			//responseDto.setResult(StatusConstant.SUCCESS);
			responseDto.setErrorInfo("");
			return responseDto;
		}else {
			//responseDto.setResult(StatusConstant.FAILURE);
			responseDto.setErrorInfo("");
			return responseDto;
		}
	}


	/**
	 * 修改用户职称
	 *
	 *
	 */
	@RequestMapping(value = "/updateUserTitle", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PractitionerUserDTO> updateUserTitle(@RequestBody PractitionerUserDTO practitionerUserDTO,
													 HttpServletRequest request) {
		UserInfoDTO userInfo = userService.getUserFromJedis(request);
		PractitionerUserDTO practitionerUserInfo = userService.getPractitionerUserInfo(userInfo.getId());
		practitionerUserInfo.setTitle(practitionerUserDTO.getTitle());
		ResponseDTO<PractitionerUserDTO> responseDto = new ResponseDTO<PractitionerUserDTO>();
		if (practitionerUserService.updateSysPractitionerUser(practitionerUserInfo)){
			userService.updateRedisUser(request);
			//responseDto.setResult(StatusConstant.SUCCESS);
			responseDto.setErrorInfo("");
			return responseDto;
		}else {
			//responseDto.setResult(StatusConstant.FAILURE);
			responseDto.setErrorInfo("");
			return responseDto;
		}
	}

	/**
	 * 修改用户医院
	 *
	 *
	 */
	@RequestMapping(value = "/updateUserHospitalName", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PractitionerUserDTO> updateUserHospitalName(@RequestBody PractitionerUserDTO practitionerUserDTO,
													HttpServletRequest request) {
		UserInfoDTO userInfo = userService.getUserFromJedis(request);
		PractitionerUserDTO practitionerUserInfo = userService.getPractitionerUserInfo(userInfo.getId());
		practitionerUserInfo.setHospitalName(practitionerUserDTO.getHospitalName());
		ResponseDTO<PractitionerUserDTO> responseDto = new ResponseDTO<PractitionerUserDTO>();
		if (practitionerUserService.updateSysPractitionerUser(practitionerUserInfo)){
			userService.updateRedisUser(request);
			//responseDto.setResult(StatusConstant.SUCCESS);
			responseDto.setErrorInfo("成功");
			return responseDto;
		}else {
			//responseDto.setResult(StatusConstant.FAILURE);
			responseDto.setErrorInfo("失败");
			return responseDto;
		}
	}


}
