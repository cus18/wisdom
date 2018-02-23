package com.wisdom.wechat.client;


import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.common.dto.core.user.UserInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("core-service")
public interface CoreServiceClient {


    @RequestMapping(value = "/getUserInfoValue",method=RequestMethod.GET)
    ResponseDTO<UserInfoDTO> getUserInfo(@RequestParam(value = "loginToken") String loginToken);


    @RequestMapping(value = "/sendIdentifyingMessage",method=RequestMethod.GET)
    void sendIdentifyingMessage(@RequestParam(value = "phoneNum") String phoneNum);

    @RequestMapping(value = "/validateCode",method=RequestMethod.GET)
    boolean validateCode(@RequestParam(value = "phoneNum") String phoneNum,@RequestParam(value = "validateCode") String validateCode);

}
