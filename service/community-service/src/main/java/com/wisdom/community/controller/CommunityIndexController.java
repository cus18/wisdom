package com.wisdom.community.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.basic.BannerDTO;
import com.wisdom.common.dto.community.activity.ActivityDTO;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.community.client.CoreServiceClient;
import com.wisdom.community.interceptor.LoginRequired;
import com.wisdom.community.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 直播板块
 * @author frank
 * @date 2015-10-14
 */

@Controller
@RequestMapping(value = "index")
public class CommunityIndexController {

	@Autowired
	CoreServiceClient coreServiceClient;

	@Autowired
	ActivityService activityService;

	/**
	 * 获取 Banner 图
	 *
	 */
	@RequestMapping(value = "bannerList", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<List<BannerDTO>> bannerList() {
		ResponseDTO responseDto=new ResponseDTO<>();
		ResponseDTO<List<BannerDTO>> listValue = coreServiceClient.getBannerList();
		responseDto.setResponseData(listValue.getResponseData());
		responseDto.setResult(StatusConstant.SUCCESS);
		return responseDto;
	}

	/**
	 * 获取首页活动
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "activityListByFirstPage", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<List<ActivityDTO>> activityListByFirstPage(HttpServletRequest request) {

		ResponseDTO<List<ActivityDTO>> responseDTO = new ResponseDTO<>();

		/****
		 获取系统中活动列表信息，每条信息的内容参考List<com.yhl.laoyou.modules.activityService.entity.ActivityDTO>
		 *****/
		responseDTO.setResponseData(activityService.activityListByFirstPage());

		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}



}
