package com.wisdom.living.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.basic.BannerDTO;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.living.client.CoreServiceClient;
import com.wisdom.living.interceptor.LoginRequired;
import com.wisdom.living.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 居家服务板块
 * @author frank
 * @date 2015-10-14
 */

@Controller
@RequestMapping(value = "index")
public class LivingIndexController {

	@Autowired
	CoreServiceClient coreServiceClient;

	/**
	 * 获取 living service 列表，
	 * 分short time service和long time service
	 * livingServiceType参数为short或者long
	 *
	 */
	@RequestMapping(value = "livingServiceList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<LivingServiceDTO>> livingServiceList(@RequestParam String livingServiceType) {
		ResponseDTO responseDto=new ResponseDTO<>();
		ResponseDTO<List<BannerDTO>> listValue = coreServiceClient.getBannerList();
		responseDto.setResponseData(listValue.getResponseData());
		responseDto.setResult(StatusConstant.SUCCESS);
		return responseDto;
	}

}
