package com.wisdom.core.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.basic.BannerDTO;
import com.wisdom.common.dto.basic.EasemobGroup;
import com.wisdom.common.dto.community.activity.UserEasemobGroupDTO;
import com.wisdom.common.dto.core.DictDTO;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.common.dto.core.user.EasemobGroupDTO;
import com.wisdom.common.dto.core.user.ElderUserDTO;
import com.wisdom.common.dto.core.user.UserInfoDTO;
import com.wisdom.common.util.DaHanTricomSMSMessageUtil;
import com.wisdom.core.mapper.DaHanTricomMessageMapper;
import com.wisdom.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ServiceController {


    @Autowired
    DictService dictService;

    @Autowired
    EasemobService easemobService;

    @Autowired
    UserService userService;

    @Autowired
    BannerService bannerService;

    @Autowired
    LoginService loginService;

    @Autowired
    DaHanTricomMessageMapper daHanTricomMessageMapper;

    //短信发送验证码
    @RequestMapping(value = "/sendIdentifyingMessage", method = {RequestMethod.POST, RequestMethod.GET})
    void sendIdentifyingMessage(@RequestParam String phoneNum) {
        loginService.sendMessage(phoneNum);
    }

    //验证验证码有效性
    @RequestMapping(value = "/validateCode", method = {RequestMethod.POST, RequestMethod.GET})
    boolean validateCode(@RequestParam String phoneNum, @RequestParam String validateCode) {
        return daHanTricomMessageMapper.searchIdentify(phoneNum, validateCode) > 0 ? true : false;
    }

    @RequestMapping(value = "/sendEasemobMessage", method = {RequestMethod.POST, RequestMethod.GET})
    void sendEasemobMessage(@RequestParam String easemobGroup, @RequestParam String message) {
        EasemobService.sendEasemobMessage(easemobGroup, message);
    }

    @RequestMapping(value = "/getEasemobMessageUrl", method = {RequestMethod.POST, RequestMethod.GET})
    String getEasemobMessageUrl(@RequestParam String... params) {
        return easemobService.getEasemobMessageUrl(params);
    }

    @RequestMapping(value = "/getEasemobGroup", method = {RequestMethod.POST, RequestMethod.GET})
    EasemobGroupDTO getEasemobGroup(@RequestParam String elderId) {
        return easemobService.getEasemobGroup(elderId);
    }

    /**
     * 获取用户所有的亲友圈中亲友的信息
     *
     * @return
     */
    @RequestMapping(value = "findDictListByInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<List<DictDTO>> findDictListByInfo(@RequestBody DictDTO dictDTO) {

        ResponseDTO<List<DictDTO>> responseDTO = new ResponseDTO<>();

        /**
         * 获取用户所有的亲友圈中亲友的信息，将用户的亲友圈的亲友信息放入RelativeElderDTO中
         * */
        responseDTO.setResponseData(dictService.findDictListByInfo(dictDTO));
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 获取登录用户的基础信息
     *
     * @return
     */
    @RequestMapping(value = "/getUserInfoValue", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<ElderUserDTO> getUserInfo(@RequestParam(value = "loginToken") String loginToken) {
        ResponseDTO responseDto = new ResponseDTO();
        UserInfoDTO userInfoDTO = userService.getUserFromLoginToken(loginToken);
        responseDto.setResponseData(userInfoDTO);
        responseDto.setResult(StatusConstant.SUCCESS);
        return responseDto;
    }


    /**
     * 获取 Banner 图
     */
    @RequestMapping(value = "/bannerList", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<List<BannerDTO>> bannerList() {
        ResponseDTO responseDto = new ResponseDTO<>();
        List<BannerDTO> list = bannerService.getBannerList();
        responseDto.setResponseData(list);
        responseDto.setResult(StatusConstant.SUCCESS);
        return responseDto;
    }

    @RequestMapping(value = "/getUserGroupChatInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO<Object> getUserGroupChatInfo(@RequestParam String elderId, @RequestParam String easemobId) {
        ResponseDTO<Object> responseDto = new ResponseDTO<>();
        UserEasemobGroupDTO userEasemobGroupDTO = new UserEasemobGroupDTO();
        EasemobGroupDTO easemobGroupDTO = easemobService.getEasemobGroup(elderId);
        if (easemobGroupDTO != null) {
            EasemobGroup easemobGroup = new EasemobGroup();
            easemobGroup.setOwner(easemobGroupDTO.getOwner());
            easemobGroup.setNurse(easemobGroupDTO.getNurse() == null ? "" : easemobGroupDTO.getNurse());
            easemobGroup.setGroupName(easemobGroupDTO.getGroupName());
            easemobGroup.setCreate_date(easemobGroupDTO.getCreate_date());
            easemobGroup.setDoctorIDArray(easemobGroupDTO.getDoctorIDArray());
            easemobGroup.setEasemobGroupID(easemobGroup.getEasemobGroupID());
            easemobGroup.setElderEasemobID(easemobGroupDTO.getElderEasemobID());
            userEasemobGroupDTO.setEasemobGroup(easemobGroup);
        }
        userEasemobGroupDTO.setActivityEasemobGroupInfoList(easemobService.getUserActivityGroupInfo(easemobId));
        responseDto.setResponseData(userEasemobGroupDTO);
        responseDto.setResult(StatusConstant.SUCCESS);
        return responseDto;
    }


    /**
     * 注册环信用户
     */
    @RequestMapping(value = "/signEasemobUser", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO signEasemobUser(@RequestParam String userID, @RequestParam String password, @RequestParam String... nickname) throws Exception {
        ResponseDTO responseDto = new ResponseDTO<>();
        responseDto.setResponseData(easemobService.signEasemobUser(userID, password, nickname));
        responseDto.setResult(StatusConstant.SUCCESS);
        return responseDto;
    }

    /**
     * 用户加入环信群组
     */
    @RequestMapping(value = "/joinEasemobGroup", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    boolean joinEasemobGroup(@RequestParam String groupID, @RequestParam String easemobID) throws Exception {
        ResponseDTO responseDto = new ResponseDTO<>();
        return easemobService.signEasemobUser(groupID, easemobID);
    }

}
