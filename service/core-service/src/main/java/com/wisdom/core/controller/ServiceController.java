package com.wisdom.core.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.basic.BannerDTO;
import com.wisdom.common.dto.core.DictDTO;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.common.dto.core.user.EasemobGroupDTO;
import com.wisdom.common.dto.core.user.ElderUserDTO;
import com.wisdom.common.dto.core.user.UserInfoDTO;
import com.wisdom.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ServiceController {


	@Autowired
	DictService dictService;

	@Autowired
	EasemobService easemobService;

	@Autowired
	UserService userService;

	@Autowired
	BannerService bannerService;

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

	/**
	 * 获取用户所有的亲友圈中亲友的信息
	 * @return
	 */
	@RequestMapping(value = "findDictListByInfo", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<List<DictDTO>> findDictListByInfo(@RequestBody DictDTO dictDTO) {

		ResponseDTO<List<DictDTO>> responseDTO = new ResponseDTO<>();

		/**
		 * 获取用户所有的亲友圈中亲友的信息，将用户的亲友圈的亲友信息放入RelativeElderDTO中
		 * */
		responseDTO.setResponseData(dictService.findDictListByInfo(dictDTO));
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 获取登录用户的基础信息
	 * @return
	 */
	@RequestMapping(value = "/getUserInfoValue", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<ElderUserDTO> getUserInfo(@RequestParam(value = "loginToken") String loginToken) {
		ResponseDTO responseDto = new ResponseDTO();
		UserInfoDTO userInfoDTO = userService.getUserFromLoginToken(loginToken);
		responseDto.setResponseData(userInfoDTO);
		responseDto.setResult(StatusConstant.SUCCESS);
		return responseDto;
	}


	/**
	 * 获取 Banner 图
	 *
	 */
	@RequestMapping(value = "/bannerList", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<List<BannerDTO>> bannerList() {
		ResponseDTO responseDto=new ResponseDTO<>();
		List<BannerDTO> list = bannerService.getBannerList();
		responseDto.setResponseData(list);
		responseDto.setResult(StatusConstant.SUCCESS);
		return responseDto;
	}

	@RequestMapping(value = "/getUserGroupChatInfo", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody
	ResponseDTO<Object> getUserGroupChatInfo(HttpServletRequest request) {
//		ResponseDTO<Object> responseDto = new ResponseDTO<>();
//		UserEasemobGroupDTO userEasemobGroupDTO=new UserEasemobGroupDTO();
//		userEasemobGroupDTO.setEasemobGroup(easemobService.getEasemobGroup(UserService.getUser(request).getSysElderUserDTO().getId()));
//		userEasemobGroupDTO.setActivityEasemobGroupInfoList(activityService.getUserActivityEasemobGroupList(UserService.getUser(request).getSysElderUserDTO().getEasemobID()));
//		responseDto.setResponseData(userEasemobGroupDTO);
//		responseDto.setResult(StatusConstant.SUCCESS);
//		return responseDto;
		return null;
	}


}
