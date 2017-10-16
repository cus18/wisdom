package com.wisdom.health.client;

import com.wisdom.common.dto.ResponseDTO;
import com.wisdom.common.dto.userService.RelativeElderDTO;
import com.wisdom.common.dto.userService.UserInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("user-service")
public interface UserServiceClient {

    @RequestMapping(value = "/relativeElderInfo",method = RequestMethod.GET)
    ResponseDTO<List<RelativeElderDTO>> relativeElderInfo(UserInfoDTO userInfoDTO);

}
