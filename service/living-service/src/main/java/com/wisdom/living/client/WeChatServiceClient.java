package com.wisdom.living.client;

import com.wisdom.common.dto.core.user.UserInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@FeignClient("wechat-service")
public interface WeChatServiceClient {

    @RequestMapping(value = "getWeChatToken", method = {RequestMethod.GET})
    String getWeChatToken();
}
