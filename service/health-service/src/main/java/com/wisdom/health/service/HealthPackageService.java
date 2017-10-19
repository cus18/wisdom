package com.wisdom.health.service;

import com.wisdom.common.dto.core.PageParamDTO;
import com.wisdom.common.dto.core.PaginationDTO;
import com.wisdom.common.dto.health.BasicInfoDTO;
import com.wisdom.common.dto.health.HealthServicePackageDTO;
import com.wisdom.common.dto.health.HealthServicePackageTemplateDTO;
import com.wisdom.common.dto.health.MemberDTO;
import com.wisdom.common.dto.core.user.EasemobGroupDTO;
import com.wisdom.common.util.TimeUtils;
import com.wisdom.health.client.CoreServiceClient;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@Transactional(readOnly = false)
public class HealthPackageService {

    @Autowired
    protected MongoTemplate mongoTemplate;
    
    @Autowired
    CoreServiceClient CoreServiceClient;

    private static ExecutorService threadExecutor = Executors.newSingleThreadExecutor();

    public void sendEasemobMessage(String easemobGroup,String message)
    {
        CoreServiceClient.sendEasemobMessage(easemobGroup,message);
    }

    public class sendEasemobMessage extends Thread
    {

        private String easemobGroup;
        private String message;

        public sendEasemobMessage(String easemobGroup,String message) {
            this.easemobGroup = easemobGroup;
            this.message = message;
        }

        @Override
        public void run() {
            sendEasemobMessage(easemobGroup,message);
        }
    }
    
    public List<HealthServicePackageDTO> getHealthServicePackageList(PageParamDTO<MemberDTO> pageParamDto) throws Exception {

        Query query = null;
        if(pageParamDto.getOrderType().equals("1"))
        {
            if(pageParamDto.getOrderBy().equals("0"))
            {
                query = new Query(where("elderId").is(pageParamDto.getRequestData().getElderId()).andOperator(where("status").is("ongoing"))).with(new Sort(Sort.Direction.DESC, "updateDate"));
            }
            else if(pageParamDto.getOrderBy().equals("0"))
            {
                query = new Query(where("elderId").is(pageParamDto.getRequestData().getElderId()).andOperator(where("status").is("ongoing"))).with(new Sort(Sort.Direction.ASC, "updateDate"));
            }

        }

        long totalCount = this.mongoTemplate.count(query, "healthServicePackage");
        PaginationDTO<HealthServicePackageTemplateDTO> page = new PaginationDTO<HealthServicePackageTemplateDTO>(Integer.parseInt(pageParamDto.getPageNo()),
                Integer.parseInt(pageParamDto.getPageSize()), totalCount);
        query.skip(page.getFirstResult());  // skip相当于从那条记录开始
        query.limit(Integer.parseInt(pageParamDto.getPageSize()));  // 从skip开始,取多少条记录
        List<HealthServicePackageDTO> data = this.mongoTemplate.find(query, HealthServicePackageDTO.class, "healthServicePackage");

        for(HealthServicePackageDTO healthServicePackageDto : data)
        {
            healthServicePackageDto.setUpdateDate(TimeUtils.formatTimeEight(healthServicePackageDto.getUpdateDate()));
        }

        return data;
    }
    
    public List<HealthServicePackageTemplateDTO> getHealthServicePackageTemplateList(PageParamDTO pageParamDto) throws Exception {

        Query query = null;
        if(pageParamDto.getOrderType().equals("1"))
        {
            if(pageParamDto.getOrderBy().equals("0"))
            {
                query = new Query().with(new Sort(Sort.Direction.DESC, "updateDate"));
            }
            else if(pageParamDto.getOrderBy().equals("0"))
            {
                query = new Query().with(new Sort(Sort.Direction.ASC, "updateDate"));
            }

        }

        long totalCount = this.mongoTemplate.count(query, "healthServicePackage_template");
        PaginationDTO<HealthServicePackageTemplateDTO> page = new PaginationDTO<HealthServicePackageTemplateDTO>(Integer.parseInt(pageParamDto.getPageNo()),
                Integer.parseInt(pageParamDto.getPageSize()), totalCount);
        query.skip(page.getFirstResult());// skip相当于从那条记录开始
        query.limit(Integer.parseInt(pageParamDto.getPageSize()));// 从skip开始,取多少条记录
        List<HealthServicePackageTemplateDTO> data = this.mongoTemplate.find(query, HealthServicePackageTemplateDTO.class, "healthServicePackage_template");

        for(HealthServicePackageTemplateDTO healthServicePackageTemplateDto : data)
        {
            healthServicePackageTemplateDto.setUpdateDate(TimeUtils.formatTimeEight(healthServicePackageTemplateDto.getUpdateDate()));
        }

        return data;
    }
    
    public HealthServicePackageTemplateDTO getHealthServicePackageTemplateDetail(String healthServicePackageTemplateId) throws Exception {

        Query query = new Query(where("healthServicePackageTemplateId").is(healthServicePackageTemplateId));
        List<HealthServicePackageTemplateDTO> data = this.mongoTemplate.find(query, HealthServicePackageTemplateDTO.class, "healthServicePackage_template");
        return data.get(0);
    }
    
    public HealthServicePackageDTO createHealthServicePackage(HealthServicePackageDTO healthServicePackageDto) throws Exception {

        healthServicePackageDto.setContractNo(UUID.randomUUID().toString());
        healthServicePackageDto.setServicePackageId(UUID.randomUUID().toString());
        healthServicePackageDto.setStatus("ongoing");
        healthServicePackageDto.setUpdateDate(new Date());
        try
        {
            this.mongoTemplate.insert(healthServicePackageDto,"healthServicePackage");
        }
        catch (Exception e)
        {
            throw new Exception("create healthServicePackage failure");
        }
        String message = CoreServiceClient.getEasemobMessageUrl("chatType1",healthServicePackageDto.getServicePackageId());

        EasemobGroupDTO easemobGroupDTO = CoreServiceClient.getEasemobGroup(healthServicePackageDto.getElderId());
        Runnable thread = new sendEasemobMessage(easemobGroupDTO.getEasemobGroupID(),message);
        threadExecutor.execute(thread);

        return healthServicePackageDto;
    }
    
    public HealthServicePackageDTO getOnGoingHealthServicePackage(String healthPackageServiceId) {
        Query query = new Query(where("servicePackageId").is(healthPackageServiceId));
        List<HealthServicePackageDTO> data = this.mongoTemplate.find(query, HealthServicePackageDTO.class, "healthServicePackage");
        return data.get(0);
    }
    
    public MemberDTO getElderContactInfo(String elderId) {
        MemberDTO memberDto = new MemberDTO();
        Query query = new Query(where("elderId").is(elderId));
        List<BasicInfoDTO> data = this.mongoTemplate.find(query, BasicInfoDTO.class, "healthArchive_basicInfo");
        memberDto.setElderId(data.get(0).getElderId());
        memberDto.setMemberName(data.get(0).getElderName());
        String memberExtendData = null;
        HashMap<String,Object> memberExtendValue = new HashMap<>();
        memberExtendValue.put("mobile", "13121278331");
        memberExtendValue.put("telephone", "01057879932");
        memberExtendValue.put("address","北京市朝阳区望京小区");
        memberExtendValue.put("emergencyContactName", "刘涛");
        memberExtendValue.put("emergencyContactPhone", "13238492991");
        JSONObject jsonObject = JSONObject.fromObject(memberExtendValue);
        memberExtendData = jsonObject.toString();
        memberDto.setMemberExtendData(memberExtendData);
        return memberDto;
    }
}
