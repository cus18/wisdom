package com.wisdom.community.client;

import com.wisdom.common.dto.basic.WeChatUserInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("wechat-service")
public interface WeChatServiceClient {

    @RequestMapping(value = "/getWechatUserInfo", method = {RequestMethod.GET,RequestMethod.POST})
    WeChatUserInfo getWechatUserInfo(@RequestParam(value = "openid") String openid);
}
