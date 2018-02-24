package com.wisdom.community.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.community.activity.ActivityDTO;
import com.wisdom.common.dto.community.activity.ActivityDiscussDTO;
import com.wisdom.common.dto.community.activity.ActivityDiscussReplyDTO;
import com.wisdom.common.dto.core.PageParamDTO;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.community.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 社区活动
 * @author frank
 * @date 2015-10-14
 */

@Controller
@RequestMapping(value = "activity")
public class ActivityController {


	@Autowired
	ActivityService activityService;

	/**
	 * 获取所有活动的列表
	 *
	 *  input PageParamDTO<String>
	 *  注意：requestData此处为String类型，
	 *  当为0的时候，表示是所有的活动列表，不管用户参加或者没有参加的
	 *  当为1的时候，表示是用户自己已经报名参加的所有活动的列表
	 *  当为2的时候，表示是系统里面的热门活动列表，依据是报名参加人数最多的活动
	 *
	 *  output ResponseDTO<List<com.yhl.laoyou.modules.activityService.entity.ActivityDTO>> 比例搭配，没有开始:进行中:已结束 比例为4:3:3
	 *
	 */
	@RequestMapping(value = "activityList", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<List<ActivityDTO>> activityList(@RequestBody PageParamDTO<String> pageParamDTO,
												HttpServletRequest request) {
		ResponseDTO<List<ActivityDTO>> responseDTO = new ResponseDTO<>();
		/****
		 获取系统中活动列表信息，每条信息的内容参考List<com.yhl.laoyou.modules.activityService.entity.ActivityDTO>
		 *****/
		responseDTO.setResponseData(activityService.getActivityList(null,pageParamDTO.getPageNo(),
				pageParamDTO.getRequestData()));
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 获取某个活动的详细信息
	 *
	 *  input activityId,
	 *
	 *  output ResponseDTO<com.yhl.laoyou.modules.activityService.entity.ActivityDTO>
	 *
	 */
	@RequestMapping(value = "activityDetail", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<ActivityDTO> activityDetail(@RequestParam String activityId) {
		ResponseDTO<ActivityDTO> responseDTO = new ResponseDTO<>();
		/****
		 根据活动的ID号，获取活动的详细信息，放入ActivityDTO中
		 *****/
		responseDTO.setResponseData(activityService.getActivity(activityId));
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 获取用户是否参加了某个活动的状态
	 *
	 *  input activityId
	 *
	 *  output ResponseDTO<String> 如果String为"1"代表已经报名参加，如果为"0"，则代表没有报名参加
	 *
	 */
	@RequestMapping(value = "activityAttendStatus", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<String> activityAttendStatus(@RequestParam String activityId,@RequestParam String openId) {
		ResponseDTO<String> responseDTO = new ResponseDTO<>();
		/****
		 根据活动的ID号，和用户的信息，判断此用户是否报名参加了此活动，"1"代表已经报名参加，如果为"0"，则代表没有报名参加
		 *****/
		responseDTO.setResponseData(activityService.getActivityAttendStatus(activityId,openId)>0?"1":"0");
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 获取某个活动的评论信息
	 *
	 *  input activityId,
	 *
	 *  output ResponseDTO<List<ActivityDiscussDTO>>
	 *
	 */
	@RequestMapping(value = "activityDiscuss", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<List<ActivityDiscussDTO>> activityDiscuss(@RequestBody PageParamDTO<String> pageParamDTO) {
		ResponseDTO<List<ActivityDiscussDTO>> responseDTO = new ResponseDTO<>();
		/****
		 根据活动的ID号，获取活动的评论信息，放入List<ActivityDiscussDTO>中
		 *****/
		responseDTO.setResponseData(activityService.getActivityDiscuss(pageParamDTO.getRequestData(),Integer.parseInt(pageParamDTO.getPageNo())*Integer.parseInt(pageParamDTO.getPageSize())));
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}


	/**
	 * 用户报名参加某个活动
	 *
	 *  input PageParamDTO<List<String>> String中放入的是报名的用户的elderId列表,['vjwioejgewoi','vwejoigjweoigj','fiweohgwng']
	 *
	 *  output ResponseDTO<String> 返回的String中，此次活动所对应的群组ID
	 *
	 */
	@RequestMapping(value = "joinActivity", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<String> joinActivity(@RequestParam String openid,
									 @RequestParam String activityId,
									 HttpServletRequest request) {
		ResponseDTO responseDTO = new ResponseDTO<>();
		/****
		 List<String>中放入的是报名参加活动的用户列表，为用户的elderId值，['vjwioejgewoi','vwejoigjweoigj','fiweohgwng']
		 *****/
		//responseData里面放入的是此次活动所对应的群组ID
		responseDTO.setResponseData(activityService.addActivityUser(activityId,openid));
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 用户针对对某个活动的发表评论
	 *
	 *  input activityDiscussDTO,
	 *
	 *  output ResponseDTO<List<ActivityDiscussDTO>>
	 *
	 */
	@RequestMapping(value = "activityDiscuss/create", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO CreateActivityDiscuss(@RequestBody ActivityDiscussDTO activityDiscussDTO) {
		ResponseDTO<List<ActivityDiscussDTO>> responseDTO = new ResponseDTO<>();
		/****
		 根据活动的ID号，用户对某个活动发表评论
		 *****/
		activityService.addActivityDiscuss(activityDiscussDTO);

		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 用户针对对某个活动的发表评论
	 *
	 *  input activityDiscussDTO,
	 *
	 *  output ResponseDTO<List<ActivityDiscussDTO>>
	 *
	 */
	@RequestMapping(value = "activityDiscuss/reply", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO replyActivityDiscuss(@RequestBody ActivityDiscussReplyDTO activityDiscussReplyDTO) {
		ResponseDTO<List<ActivityDiscussDTO>> responseDTO = new ResponseDTO<>();
		/****
		 根据活动的ID号，用户对某个活动发表评论
		 *****/
		activityService.addActivityDiscussReply(activityDiscussReplyDTO);

		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

}
