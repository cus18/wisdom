package com.wisdom.community.client;

import com.wisdom.common.dto.community.course.OnlineCourseDTO;
import com.wisdom.common.dto.core.DictDTO;
import com.wisdom.common.dto.core.Page;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.common.dto.core.user.EasemobGroupDTO;
import com.wisdom.common.dto.core.user.ElderUserDTO;
import com.wisdom.common.dto.core.user.RelativeElderDTO;
import com.wisdom.common.dto.core.user.UserInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@FeignClient("core-service")
public interface CoreServiceClient {

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

    @RequestMapping(value = "/getEasemobGroupIDByElderID",method=RequestMethod.GET)
    String getEasemobGroupIDByElderID(String elderId);

    @RequestMapping(value = "/findDictListByInfo",method=RequestMethod.POST)
    ResponseDTO<List<DictDTO>> findDictListByInfo(@RequestBody DictDTO dictDTO);

}
