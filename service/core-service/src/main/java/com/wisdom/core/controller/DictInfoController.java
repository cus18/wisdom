package com.wisdom.core.controller;


import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.core.DictDTO;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.core.interceptor.LoginRequired;
import com.wisdom.core.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DictInfoController {

	@Autowired
	DictService dictService;

	/**
	 * 获取用户所有的亲友圈中亲友的信息
	 * @return
	 */
	@RequestMapping(value = "findDictListByInfo", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
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

}
