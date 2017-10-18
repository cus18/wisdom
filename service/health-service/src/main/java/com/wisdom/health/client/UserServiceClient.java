package com.wisdom.health.client;

import com.wisdom.common.dto.Page;
import com.wisdom.common.dto.ResponseDTO;
import com.wisdom.common.dto.userService.EasemobGroupDTO;
import com.wisdom.common.dto.userService.ElderUserDTO;
import com.wisdom.common.dto.userService.RelativeElderDTO;
import com.wisdom.common.dto.userService.UserInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@FeignClient("user-service")
public interface UserServiceClient {

    @RequestMapping(value = "/relativeElderInfo",method=RequestMethod.GET)
    ResponseDTO<List<RelativeElderDTO>> relativeElderInfo(UserInfoDTO userInfoDTO);

    @RequestMapping(value = "/sendEasemobMessage",method=RequestMethod.GET)
    void sendEasemobMessage(@RequestParam(value = "easemobGroup") String easemobGroup, @RequestParam(value = "message") String message);

    @RequestMapping(value = "/getEasemobMessageUrl",method=RequestMethod.GET)
    String getEasemobMessageUrl(String... params);

    @RequestMapping(value = "/getEasemobGroup",method=RequestMethod.GET)
    EasemobGroupDTO getEasemobGroup(String elderId);

    @RequestMapping(value = "/getUserInfo",method=RequestMethod.GET)
    UserInfoDTO getUserInfo(HttpServletRequest request);

    @RequestMapping(value = "/getElderUserByHospitalID",method=RequestMethod.GET)
    Page<ElderUserDTO> getElderUserByHospitalID(@RequestParam(value = "id") String id,
                                                @RequestParam(value = "page") Page page);



}
