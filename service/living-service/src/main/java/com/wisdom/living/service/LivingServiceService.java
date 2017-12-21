package com.wisdom.living.service;

import com.wisdom.common.util.DaHanTricomSMSMessageUtil;
import com.wisdom.living.entity.LivingService;
import com.wisdom.living.entity.LivingServiceOffice;
import com.wisdom.living.entity.LivingServiceOrder;
import com.wisdom.living.entity.OfficeMessage;
import com.wisdom.living.mapper.LivingMapper;
import com.wisdom.living.mapper.LivingServiceOrderMapper;
import com.wisdom.living.mapper.OfficeMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = false)
public class LivingServiceService {

    @Autowired
    private LivingMapper livingServiceDao;

    @Autowired
    private LivingServiceOrderMapper livingServiceOrderMapper;

    @Autowired
    private OfficeMessageMapper officeMessageMapper;

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
        officeMessageMapper.insertOfficeMessage(officeMessage);
        DaHanTricomSMSMessageUtil.sendMsg(officeMessageMapper.getOfficePhone(livingService.getSys_office_id()),message);
    }

    public Integer delLivingServiceOrder(String livingServiceOrderID){
        return livingServiceOrderMapper.delLivingServiceOrder(livingServiceOrderID);
    }
}
