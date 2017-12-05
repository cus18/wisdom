package com.wisdom.translation.controller;

import com.aliyun.oss.common.utils.HttpUtil;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.basic.BannerDTO;
import com.wisdom.common.dto.community.activity.ActivityDTO;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.translation.client.CoreServiceClient;
import com.wisdom.translation.entity.RecognitionDTO;
import com.wisdom.translation.entity.RecognitionResponseDTO;
import com.wisdom.translation.interceptor.LoginRequired;
import com.wisdom.translation.service.ActivityService;
import com.wisdom.translation.service.RedisService;
import com.wisdom.translation.util.HttpTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 直播板块
 * @author frank
 * @date 2015-10-14
 */

@Controller
public class TranslationIndexController {

	@Autowired
    CoreServiceClient coreServiceClient;

	@Autowired
    ActivityService activityService;

	@Autowired
	RedisService redisService;

	/**
	 * 语音识别
	 *
	 */
	@RequestMapping(value = "recognition", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<String> recognition(@RequestBody RecognitionDTO recognitionDTO) {

		Map p = new HashMap();
		p.put("format", recognitionDTO.getFormat());
		p.put("rate", recognitionDTO.getRate());
		p.put("channel", 1);
		p.put("token", redisService.get("recognitionToken"));
		p.put("cuid", "aip-cxy");
		p.put("url", recognitionDTO.getUrl());
		p.put("callback", ConfigConstant.recognitionCallBack);

		Map header = new HashMap();
		header.put("Content-Type","application/json");
		String str = null;

		try {
			str = HttpTool.Instance.doPost("http://vop.baidu.com/server_api", header,p);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResponseDTO<String> responseDTO = new ResponseDTO<>();
		responseDTO.setResponseData(str);
		return responseDTO;
	}

	@RequestMapping(value = "recognitionCallBack", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	void recognitionCallBack(@RequestBody RecognitionResponseDTO recognitionResponseDTO) {

		recognitionResponseDTO.getResult();
		recognitionResponseDTO.getSn();
	}

	/**
	 * 语音翻译
	 *
	 */
	@RequestMapping(value = "translate", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ActivityDTO>> translate(HttpServletRequest request) {

		//1.创建SpeechRecognizer对象
		return null;
	}


	/**
	 * 语音合成
	 *
	 */
	@RequestMapping(value = "compose", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ActivityDTO>> compose(HttpServletRequest request) {

		//1.创建SpeechRecognizer对象


		//2.设置听写参数，详见《MSC Reference Manual》SpeechConstant类


		return null;
	}
}
