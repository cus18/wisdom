package com.wisdom.user.controller;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.ResponseDTO;
import com.wisdom.common.dto.userService.ElderUserDTO;
import com.wisdom.common.dto.userService.RelativeElderDTO;
import com.wisdom.common.dto.userService.UserInfoDTO;
import com.wisdom.user.client.HealthServiceClient;
import com.wisdom.user.interceptor.LoginRequired;
import com.wisdom.user.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class UserInfoController {


	@Autowired
	HealthServiceClient healthServiceClient;

	@Autowired
	UserService userService;

	@Autowired
	PractitionerUserService practitionerUserService;

	@Autowired
	RedisService redisService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private LoginService loginService;

	/**
	 * 获取用户所有的亲友圈中亲友的信息
	 * @return
	 */
	@RequestMapping(value = "relativeElderInfo", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<RelativeElderDTO>> relativeElderInfo(HttpServletRequest request,
														  @RequestBody UserInfoDTO userInfoDTO) {

		System.out.println(StatusConstant.LOGIN_ERROR);

		System.out.println(ConfigConstant.instance.loginTokenPeriod);

		ResponseDTO<List<RelativeElderDTO>> responseDTO = new ResponseDTO<>();

		loginService.sendMessage(userInfoDTO.getPhone());


		responseDTO.setResult(StatusConstant.instance.SUCCESS);
		return responseDTO;

		/**
		 * 获取用户所有的亲友圈中亲友的信息，将用户的亲友圈的亲友信息放入RelativeElderDTO中
		 * */
		//List<RelativeElderDTO> elderInfoList = new ArrayList<RelativeElderDTO>();
		//responseDTO.setResponseData(healthArchiveService.getRelativeList(UserService.getUser(request)));
		//responseDTO.setResult(StatusConstant.SUCCESS);

//		List<RelativeElderDTO> elderInfoList = new ArrayList<RelativeElderDTO>();
//		String test = healthServiceClient.healthServiceTest();
//		surveyService.createData();
//		surveyService.findData();
//
//		Query query = new Query().with(new Sort(Sort.Direction.ASC, "questionId"));
//		List<SurveyDTO> surveyDTOList = this.mongoTemplate.find(query, SurveyDTO.class,"surveys");
//
//		boolean isOk = redisService.set("name", "陈佳科");
//		String name = redisService.get("name");
//		redisService.expire("name",1);
//		name = redisService.get("name");
//
//		return responseDTO;
	}

	/**
	 * 获取登录用户的基础信息
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<ElderUserDTO> getUserInfo(HttpServletRequest request) {
		ResponseDTO responseDto = new ResponseDTO();
//		UserInfoDTO userInfoDTO = userService.getUserFromJedis(request);
//		responseDto.setResponseData(userInfoDTO);
		//responseDto.setResult(StatusConstant.SUCCESS);
		return responseDto;
	}


}
