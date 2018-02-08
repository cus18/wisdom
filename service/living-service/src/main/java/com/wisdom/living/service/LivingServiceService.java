package com.wisdom.living.service;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.common.util.HttpRequestUtil;
import com.wisdom.living.client.WeChatServiceClient;
import com.wisdom.living.entity.*;
import com.wisdom.living.mapper.LivingMapper;
import com.wisdom.living.mapper.LivingServiceOrderMapper;
import com.wisdom.living.mapper.OfficeMessageMapper;
import com.wisdom.living.entity.TemplateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = false)
public class LivingServiceService {

    @Autowired
    private LivingMapper livingServiceDao;

    @Autowired
    private LivingServiceOrderMapper livingServiceOrderMapper;

    @Autowired
    private OfficeMessageMapper officeMessageMapper;

    @Autowired
    private WeChatServiceClient weChatServiceClient;

    public List getLivingService(LivingService livingService) {
        List<LivingService> list= livingServiceDao.getLivingService(livingService);
        return list;
    }

    @Transactional(rollbackFor= Exception.class)
    public Integer insertLivingServiceOrder(LivingServiceOrder livingServiceOrder){
        Integer result=livingServiceOrderMapper.insertLivingServiceOrder(livingServiceOrder);
        LivingService livingService=new LivingService();
        livingService.setId(livingServiceOrder.getLivingservice_id());
        livingService.setLastNo(0);
        livingService.setNextNo(10);
        livingService=livingServiceDao.getLivingService(livingService).get(0);
        String message=livingServiceOrder.getPhone()+"刚刚在老友提醒您审核"+livingService.getName()+"服务，请您及时登录系统处理";
        OfficeMessage officeMessage=new OfficeMessage();
        officeMessage.setMessage(message);
        officeMessageMapper.insertOfficeMessage(officeMessage);
//        DaHanTricomSMSMessageUtil.sendMsg(officeMessageMapper.getOfficePhone(livingService.getSys_office_id()),message);
        WxTemplate wxTemplate=new WxTemplate();
        wxTemplate.setTemplate_id("ZFPAi-GiZAdXvbtcB494tkErnX5yMd_FbukjqXHhumc");
        wxTemplate.setTouser(livingServiceOrder.getOpenid());
        wxTemplate.setUrl("http://wechat.hlsenior.com/elder#/myselfCenter");
        Map data=new HashMap<String,String>();
        TemplateData templateData=new TemplateData();
        templateData.setValue("服务确认通知");
        data.put("first","服务确认通知");
        TemplateData templateData1=new TemplateData();
        templateData1.setValue(livingService.getOfficeName());
        data.put("keyword1",templateData1);
        TemplateData templateData2=new TemplateData();
        templateData2.setValue(livingService.getName());
        data.put("keyword2",templateData2);
        TemplateData templateData3=new TemplateData();
        templateData3.setValue(livingService.getOfficeName());
        data.put("keyword3",templateData3);
        TemplateData templateData4=new TemplateData();
        templateData4.setValue("无");
        data.put("keyword4",templateData4);
        TemplateData templateData5=new TemplateData();
        templateData5.setValue("服务商家将尽快与您电话联系，确认更多的服务细节，请保持电话畅通！");
        data.put("remark",templateData5);
        wxTemplate.setData(data);
        String json=JSONObject.toJSONString(wxTemplate);
        String token=weChatServiceClient.getWeChatToken();
        String results=HttpRequestUtil.httpPost(json,"https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token);
        return result;
    }

    public List getLivingServiceOffice(LivingServiceOffice livingServiceOffice){
        List<LivingServiceOffice> list=livingServiceDao.getLivingServiceOffice(livingServiceOffice);
        for (LivingServiceOffice l:list) {
            l.setCount(livingServiceDao.getLivingServiceOfficeCount(l.getId()));
        }
        return list;
    }

    public List getLivingServiceOrderStatus(String openID,String status){
        return livingServiceOrderMapper.getLivingServiceOrderStatus(openID,status);
    }

    public void sendMessage(String livingServiceOrderID,String userID){
        LivingServiceOrder livingServiceOrder=livingServiceOrderMapper.getLivingServiceOrder(livingServiceOrderID);
        LivingService livingService=new LivingService();
        livingService.setId(livingServiceOrder.getLivingservice_id());
        livingService.setLastNo(0);
        livingService.setNextNo(10);
        livingService=livingServiceDao.getLivingService(livingService).get(0);
        String message=livingServiceOrder.getPhone()+"刚刚在老友提醒您审核"+livingService.getName()+"服务，请您及时登录系统处理";
        OfficeMessage officeMessage=new OfficeMessage();
        officeMessage.setMessage(message);
    //        officeMessage.setSys_elder_user_id(userID);
    //            officeMessageMapper.insertOfficeMessage(officeMessage);
    //        DaHanTricomSMSMessageUtil.sendMsg(officeMessageMapper.getOfficePhone(livingService.getSys_office_id()),message);
        WxTemplate wxTemplate=new WxTemplate();
        wxTemplate.setTemplate_id("ZFPAi-GiZAdXvbtcB494tkErnX5yMd_FbukjqXHhumc");
        wxTemplate.setTouser(livingServiceOrder.getOpenid());
        wxTemplate.setUrl("http://wechat.hlsenior.com/elder#/myselfCenter");
        Map data=new HashMap<String,String>();
        TemplateData templateData=new TemplateData();
        templateData.setValue("服务确认通知");
        data.put("first","服务确认通知");
        TemplateData templateData1=new TemplateData();
        templateData1.setValue(livingService.getOfficeName());
        data.put("keyword1",templateData1);
        TemplateData templateData2=new TemplateData();
        templateData2.setValue(livingService.getName());
        data.put("keyword2",templateData2);
        TemplateData templateData3=new TemplateData();
        templateData3.setValue(livingService.getOfficeName());
        data.put("keyword3",templateData3);
        TemplateData templateData4=new TemplateData();
        templateData4.setValue("无");
        data.put("keyword4",templateData4);
        TemplateData templateData5=new TemplateData();
        templateData5.setValue("服务商家将尽快与您电话联系，确认更多的服务细节，请保持电话畅通！");
        data.put("remark",templateData5);
        wxTemplate.setData(data);
        String json=JSONObject.toJSONString(wxTemplate);
        String token=weChatServiceClient.getWeChatToken();
        String result=HttpRequestUtil.httpPost(json,"https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token);
    }

    public Integer delLivingServiceOrder(String livingServiceOrderID){
        return livingServiceOrderMapper.delLivingServiceOrder(livingServiceOrderID);
    }
}
