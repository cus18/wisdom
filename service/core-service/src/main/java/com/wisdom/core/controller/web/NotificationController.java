package com.wisdom.core.controller.web;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.core.Page;
import com.wisdom.common.dto.core.PageParamDTO;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.common.dto.core.entity.NotificationTemplateEntity;
import com.wisdom.common.dto.core.entity.RemindTemplateEntity;
import com.wisdom.common.dto.core.notification.ExtendMessageDTO;
import com.wisdom.common.dto.core.notification.NotificationMessageDTO;
import com.wisdom.common.dto.core.user.UserInfoDTO;
import com.wisdom.core.interceptor.LoginRequired;
import com.wisdom.core.service.NotificationService;
import com.wisdom.core.service.RemindService;
import com.wisdom.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 工具 Controller
 *
 * @author ThinkGem
 * @version 2013-10-17
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RemindService remindService;

    @Autowired
    UserService userService;

    /**
     *
     * 获取老友提醒消息列表
     *
     * input pageParamDTO
     *
     * response ResponseDTO<List<NotificationMessageDTO>>
     *
     */
    @RequestMapping(value="/notificationMessage",method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<NotificationMessageDTO>> notificationMessage(@RequestBody PageParamDTO pageParamDTO,
                                                                  HttpServletRequest request)
    {
        ResponseDTO<List<NotificationMessageDTO>> response = new ResponseDTO();

        /****
         老友用户的消息列表，每天消息的内容参考NotificationMessageDTO
         *****/
        Integer limit=Integer.parseInt(pageParamDTO.getPageNo())*Integer.parseInt(pageParamDTO.getPageSize());
        String elderId = userService.getUserFromLoginToken(request.getHeader("loginToken")).getElderUserDTO().getId();
        response.setResponseData(remindService.getRemindListBySysElderUserID(elderId,limit));
        response.setResult(StatusConstant.SUCCESS);
        return response;
    }

    /**
     *
     * 修改老友提醒消息中某条消息的状态，没有读，变成已读
     *
     *  input notificationMessageDTO
     *
     *  response ResponseDTO
     *
     */
    @RequestMapping(value="/notificationMessage/update",method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public @ResponseBody
    ResponseDTO updateNotificationMessage(@RequestBody NotificationMessageDTO notificationMessageDTO, HttpServletRequest request)
    {
        ResponseDTO response = new ResponseDTO();

        /****
         修改老友提醒消息中某条消息的状态，没有读，变成已读
         *****/

        try {
            remindService.updateRemindStatus(notificationMessageDTO.getNotificationMessageId());
            response.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult(StatusConstant.FAILURE);
        }
        response.setResult(StatusConstant.SUCCESS);
        return response;
    }

    /**
     * 获取用户未读的提醒消息数
     *
     * input ExtendMessageDTO
     *
     * response ResponseDTO<ExtendMessageDTO>
     *
     */
    @LoginRequired
    @RequestMapping(value="/notificationMessage/notificationMessageUnreadNum",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO notificationMessageUnreadNum(HttpServletRequest request) {
        ResponseDTO responseDto = new ResponseDTO<>();
        String elderId = userService.getUserFromLoginToken(request.getHeader("loginToken")).getElderUserDTO().getId();
        responseDto.setResponseData(remindService.getRemindByUnread(elderId));
        responseDto.setResult(StatusConstant.SUCCESS);
        return responseDto;
    }

    /**
     * 获取扩展运营等信息的消息列表
     *
     * input PageParamDTO
     *
     * response ResponseDTO<List<ExtendMessageDTO>
     */
    @RequestMapping(value="/extendMessage",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO<List<ExtendMessageDTO>> extendMessage(@RequestBody PageParamDTO pageParamDTO, HttpServletRequest request) {
        ResponseDTO<List<ExtendMessageDTO>> responseDto = new ResponseDTO<>();

        /****
         扩展运营等信息的消息列表，每条信息的内容参考ExtendMessageDTO
         *****/
        List<ExtendMessageDTO> extendMessageDTOList = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            ExtendMessageDTO extendMessageDTO = new ExtendMessageDTO();
            extendMessageDTO.setExtendMessageId(UUID.randomUUID().toString());
            extendMessageDTO.setExtendMessageLogo("http://yhllaoyou.oss-cn-beijing.aliyuncs.com/1499506426014.jpg");
            extendMessageDTO.setExtendMessageDate(new Date());
            extendMessageDTO.setExtendMessageName("房山区养老院老年歌舞交际会隆重举办");
            extendMessageDTOList.add(extendMessageDTO);
        }
        responseDto.setResponseData(extendMessageDTOList);
        Integer limit=Integer.parseInt(pageParamDTO.getPageNo())*Integer.parseInt(pageParamDTO.getPageSize());
        notificationService.getNotificationListBySysElderUserID("",limit);
        responseDto.setResponseData(extendMessageDTOList);
        responseDto.setResult(StatusConstant.SUCCESS);
        return responseDto;
    }

    /**
     * 获取扩展运营信息中某条消息的详细信息
     *
     * input ExtendMessageDTO
     *
     * response ResponseDTO<ExtendMessageDTO>
     *
     */
    @LoginRequired
    @RequestMapping(value="/extendMessage/detail",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO<ExtendMessageDTO> extendMessageDetail(@RequestBody ExtendMessageDTO extendMessageDTO,
                                                      HttpServletRequest request) {
        ResponseDTO<ExtendMessageDTO> responseDto = new ResponseDTO<>();
        UserInfoDTO user = userService.getUserFromLoginToken(request.getHeader("loginToken"));

        /****
         扩展运营等信息的消息列表，某条消息的详细信息
         *****/
        extendMessageDTO.setExtendMessageName("房山区养老院老年歌舞交际会隆重举办");
        extendMessageDTO.setExtendMessageDate(new Date());
        extendMessageDTO.setExtendMessageLogo("http://yhllaoyou.oss-cn-beijing.aliyuncs.com/1499506426014.jpg");
        extendMessageDTO.setExtendMessageContent("富文本编辑器编写的所有内容我是详细的通知内容,富文本编辑器编写的所有内容我是详细的通知内容," +
                "富文本编辑器编写的所有内容我是详细的通知内容,富文本编辑器编写的所有内容我是详细的通知内容,富文本编辑器编写的所有内容我是详细的通知内容" +
                "富文本编辑器编写的所有内容我是详细的通知内容,富文本编辑器编写的所有内容我是详细的通知内容,富文本编辑器编写的所有内容我是详细的通知内容");


        responseDto.setResponseData(extendMessageDTO);
        responseDto.setResponseData(notificationService.getNotificationByID(extendMessageDTO.getExtendMessageId()));
        responseDto.setResult(StatusConstant.SUCCESS);
        return responseDto;
    }


    /**
     * 获取用户未读的通知消息数
     *
     * input ExtendMessageDTO
     *
     * response ResponseDTO<ExtendMessageDTO>
     *
     */
    @LoginRequired
    @RequestMapping(value="/extendMessage/extendMessageUnreadNum",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO extendMessageUnreadNum(HttpServletRequest request) {
        ResponseDTO responseDto = new ResponseDTO<>();
        UserInfoDTO user = userService.getUserFromLoginToken(request.getHeader("loginToken"));
        responseDto.setResponseData(notificationService.getNotificationByUnread(user.getElderUserDTO().getId()));
        responseDto.setResult(StatusConstant.SUCCESS);
        return responseDto;
    }

    /**
     * 更新消息状态
     *
     * input ExtendMessageDTO
     *
     * response ResponseDTO<ExtendMessageDTO>
     *
     */
    @LoginRequired
    @RequestMapping(value="/extendMessage/updateExtendMessageStatus",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO updateExtendMessageStatus(@RequestBody ExtendMessageDTO extendMessageDTO) {
        ResponseDTO responseDto = new ResponseDTO<>();
        try {
            notificationService.updateNotificationStatus(extendMessageDTO.getExtendMessageId());
            responseDto.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setResult(StatusConstant.FAILURE);
        }
        return responseDto;
    }

    //****************************机构版消息与提醒接口部分

    //***消息模板部分

    //获取消息模板列表
    @LoginRequired
    @RequestMapping(value="/extendMessage/getNotificationTemplateList",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO getNotificationTemplateList(@RequestParam String searchValue,
                                            @RequestParam String sartUpdateDate,
                                            @RequestParam String endUpdateDate,
                                            @RequestParam Integer pageNo,
                                            @RequestParam Integer pageSize,
                                          HttpServletRequest request) {
        ResponseDTO responseDto = new ResponseDTO<>();
        try {
            Page page=new Page();
            page.setPageNo(pageNo);
            page.setPageSize(pageSize);
            page=notificationService.getNotificationTemplateList(searchValue,sartUpdateDate,endUpdateDate,page);
            responseDto.setResponseData(page);
            responseDto.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setResult(StatusConstant.FAILURE);
        }
        return responseDto;
    }


    //添加消息模板
    @LoginRequired
    @RequestMapping(value="/extendMessage/addNotificationTemplate",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO addNotificationTemplate(@RequestBody NotificationTemplateEntity notificationTemplateEntity,
                                        HttpServletRequest request) {
        ResponseDTO responseDto = new ResponseDTO<>();
        try {
            Integer result=notificationService.addNotificationTemplate(notificationTemplateEntity);
            responseDto.setResponseData(result);
            responseDto.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setResult(StatusConstant.FAILURE);
        }
        return responseDto;
    }

    //更新消息模板
    @LoginRequired
    @RequestMapping(value="/extendMessage/updateNotificationTemplate",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO updateNotificationTemplate(@RequestBody NotificationTemplateEntity notificationTemplateEntity,
                                        HttpServletRequest request) {
        ResponseDTO responseDto = new ResponseDTO<>();
        try {
            Integer result=notificationService.updateNotificationTemplate(notificationTemplateEntity);
            responseDto.setResponseData(result);
            responseDto.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setResult(StatusConstant.FAILURE);
        }
        return responseDto;
    }

    //删除消息模板
    @LoginRequired
    @RequestMapping(value="/extendMessage/deleteNotificationTemplate",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO deleteNotificationTemplate(@RequestParam String id,
                                           HttpServletRequest request) {
        ResponseDTO responseDto = new ResponseDTO<>();
        try {
            Integer result=notificationService.deleteNotificationTemplate(id);
            responseDto.setResponseData(result);
            responseDto.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setResult(StatusConstant.FAILURE);
        }
        return responseDto;
    }


    //获取消息模板
    @LoginRequired
    @RequestMapping(value="/extendMessage/getNotificationTemplate",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO getNotificationTemplate(@RequestParam String id,
                                           HttpServletRequest request) {
        ResponseDTO responseDto = new ResponseDTO<>();
        try {
            responseDto.setResponseData(notificationService.getNotificationTemplate(id));
            responseDto.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setResult(StatusConstant.FAILURE);
        }
        return responseDto;
    }

    //提醒模板部分

    //获取提醒模板列表
    @LoginRequired
    @RequestMapping(value="/extendMessage/getRemindTemplateEntityList",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO getRemindTemplateEntityList(@RequestParam String searchValue,
                                            @RequestParam String sartUpdateDate,
                                            @RequestParam String endUpdateDate,
                                            @RequestParam Integer pageNo,
                                            @RequestParam Integer pageSize,
                                            HttpServletRequest request) {
        ResponseDTO responseDto = new ResponseDTO<>();
        try {
            Page page=new Page();
            page.setPageNo(pageNo);
            page.setPageSize(pageSize);
            page=remindService.getRemindTemplateEntityList(searchValue,sartUpdateDate,endUpdateDate,page);
            responseDto.setResponseData(page);
            responseDto.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setResult(StatusConstant.FAILURE);
        }
        return responseDto;
    }


    //添加消息模板
    @LoginRequired
    @RequestMapping(value="/extendMessage/addRemindTemplate",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO addRemindTemplate(@RequestBody RemindTemplateEntity remindTemplateEntity,
                                        HttpServletRequest request) {
        ResponseDTO responseDto = new ResponseDTO<>();
        try {
            Integer result=remindService.addRemindTemplate(remindTemplateEntity);
            responseDto.setResponseData(result);
            responseDto.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setResult(StatusConstant.FAILURE);
        }
        return responseDto;
    }

    //更新消息模板
    @LoginRequired
    @RequestMapping(value="/extendMessage/updateRemindTemplate",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO updateRemindTemplate(@RequestBody RemindTemplateEntity remindTemplateEntity,
                                           HttpServletRequest request) {
        ResponseDTO responseDto = new ResponseDTO<>();
        try {
            Integer result=remindService.updateRemindTemplate(remindTemplateEntity);
            responseDto.setResponseData(result);
            responseDto.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setResult(StatusConstant.FAILURE);
        }
        return responseDto;
    }

    //删除消息模板
    @LoginRequired
    @RequestMapping(value="/extendMessage/deleteRemindTemplate",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO deleteRemindTemplate(@RequestParam String id,
                                           HttpServletRequest request) {
        ResponseDTO responseDto = new ResponseDTO<>();
        try {
            Integer result=remindService.deleteRemindTemplate(id);
            responseDto.setResponseData(result);
            responseDto.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setResult(StatusConstant.FAILURE);
        }
        return responseDto;
    }

//获取消息模板
    @LoginRequired
    @RequestMapping(value="/extendMessage/getRemindTemplateEntityByID",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO getRemindTemplateEntityByID(@RequestParam String id,
                                        HttpServletRequest request) {
        ResponseDTO responseDto = new ResponseDTO<>();
        try {
            responseDto.setResponseData(remindService.getRemindByID(id));
            responseDto.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setResult(StatusConstant.FAILURE);
        }
        return responseDto;
    }

//发送提醒消息
    @LoginRequired
    @RequestMapping(value="/extendMessage/sendRemind",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO sendRemind(@RequestParam String type,
                                            HttpServletRequest request) {
        ResponseDTO responseDto = new ResponseDTO<>();
        try {
            responseDto.setResponseData(remindService.getRemindByID(type));
            responseDto.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setResult(StatusConstant.FAILURE);
        }
        return responseDto;
    }


    //意见反馈
    @RequestMapping(value="/feedback",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO feedback(@RequestParam String text,@RequestParam String openid) {
        ResponseDTO responseDto = new ResponseDTO<>();
        try {
            remindService.addFeedback(text,openid);
            responseDto.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setResult(StatusConstant.FAILURE);
        }
        return responseDto;
    }



}
