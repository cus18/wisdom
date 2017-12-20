package com.wisdom.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.common.util.HttpRequestUtil;
import com.wisdom.common.util.SignUtil;
import com.wisdom.wechat.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


@Controller
@RestController
public class WechatController {

	@Autowired
	WechatService wechatService;

	/**
	 * *用户校验是否是微信服务器发送的请求
	 * */
	@RequestMapping(value = "/wxChat", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	String wxPatientChat(HttpServletRequest request, HttpServletResponse response) {
		String method = request.getMethod().toUpperCase();
		if ("GET".equals(method)) {
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");
			// 随机字符串
			String echostr = request.getParameter("echostr");
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				return echostr;
			}
			return "";
		} else {
			// 调用核心业务类接收消息、处理消息
			String respMessage = null;
			try {
				respMessage = wechatService.processRequest(request,response);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return respMessage;
		}
	}

		/**
		 * 微信授权
		 * 1.客户端请求获取 OpenID
		 *
		 */
		@RequestMapping(value = "getOpenID", method = {RequestMethod.POST, RequestMethod.GET})
		public void getOpenID(@RequestParam String url,HttpServletRequest request,HttpServletResponse response) throws Exception {
			url=url.replace("#","@");
			String redirectUrl= URLEncoder.encode("http://hualulaoyou.viphk.ngrok.org/wechat/returnCode?url="+url,"UTF-8");
			String requestUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx952c2a0a6b0d63c0&redirect_uri="+redirectUrl+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
			response.sendRedirect(requestUrl);
	}

	/**
	 * 微信授权
	 * 2.获取微信授权，跳转回请求链接
	 *
	 */
	@RequestMapping(value = "returnCode", method = {RequestMethod.POST, RequestMethod.GET})
	public void returnCode(@RequestParam String url,@RequestParam String code, @RequestParam String state,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String uri="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+WechatService.appid+"&secret="+WechatService.secret+"&code="+code+"&grant_type=authorization_code";
		String reulst=HttpRequestUtil.get(uri);
		Map<String,String> map= JSONObject.parseObject(reulst,Map.class);
		url=url.replace("@","#");
		response.sendRedirect(url+"?openid="+map.get("openid"));
	}




}
