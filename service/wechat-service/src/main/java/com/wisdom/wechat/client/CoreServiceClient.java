package com.wisdom.wechat.client;

import com.wisdom.common.dto.basic.BannerDTO;
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


    @RequestMapping(value = "/getUserInfoValue",method=RequestMethod.GET)
    ResponseDTO<UserInfoDTO> getUserInfo(@RequestParam(value = "loginToken") String loginToken);


}
