/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.common.util;

import com.wisdom.common.dto.LogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2014-11-7
 */
public class LogUtils {

	@Autowired
	private MongoTemplate mongoTemplate;

	private static ExecutorService threadExecutor = Executors.newCachedThreadPool();

	/**
	 * 保存日志
	 * @param request
	 * @param handler
	 * @param ex
	 * @param title
	 */
	public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title){
		LogDTO logDTO = new LogDTO();
		logDTO.setTitle(title);
		logDTO.setType(ex == null ? LogDTO.TYPE_ACCESS : LogDTO.TYPE_EXCEPTION);
		logDTO.setRemoteAddr(StringUtils.getRemoteAddr(request));
		logDTO.setUserAgent(request.getHeader("user-agent"));
		logDTO.setRequestUri(request.getRequestURI());
		logDTO.setParams(request.getParameterMap());
		logDTO.setMethod(request.getMethod());
		logDTO.setBeginDate(new Date());
		//插入用户的openId
		logDTO.setOpenId((String) request.getSession().getAttribute("openId"));
		// 异步保存日志
		Runnable thread = new SaveLogThread(logDTO, handler, ex);
		threadExecutor.execute(thread);
	}

	/**
	 * 保存日志
	 */
	public static void saveLog(String title,String parameters){
		LogDTO logDTO = new LogDTO();
		logDTO.setType(LogDTO.TYPE_EXCEPTION);
		logDTO.setTitle(title);
		logDTO.setParameters(parameters);
		// 异步保存日志
		Runnable thread = new SaveLogThread(logDTO, null, null);
		threadExecutor.execute(thread);
	}


	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread{
		private LogDTO logDTO;
		private Object handler;
		private Exception ex;
		
		public SaveLogThread(LogDTO logDTO, Object handler, Exception ex){
			super(SaveLogThread.class.getSimpleName());
			this.logDTO = logDTO;
			this.handler = handler;
			this.ex = ex;
		}
		
		@Override
		public void run() {
			//日志功能需要重写

		}
	}



}
