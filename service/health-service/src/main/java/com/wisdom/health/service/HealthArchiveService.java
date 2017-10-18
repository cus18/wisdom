package com.wisdom.health.service;

import com.wisdom.common.dto.PageParamDTO;
import com.wisdom.common.dto.PaginationDTO;
import com.wisdom.common.dto.healthService.*;
import com.wisdom.common.dto.userService.EasemobGroupDTO;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.TimeUtils;
import com.wisdom.health.client.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@Transactional(readOnly = false)
public class HealthArchiveService {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    private UserServiceClient userServiceClient;

    private static ExecutorService threadExecutor = Executors.newSingleThreadExecutor();

    public void sendEasemobMessage(String easemobGroup, String message) {
        userServiceClient.sendEasemobMessage(easemobGroup, message);
    }

    public class sendEasemobMessage extends Thread {

        private String easemobGroup;
        private String message;

        public sendEasemobMessage(String easemobGroup, String message) {
            this.easemobGroup = easemobGroup;
            this.message = message;
        }

        @Override
        public void run() {
            try {
                sendEasemobMessage(easemobGroup, message);
            } catch (Exception e) {

            }
        }
    }

    public BasicInfoDTO getElderBasicInfo(String elderId) throws Exception {
        Query query = new Query(Criteria.where("elderId").is(elderId));
        BasicInfoDTO data = this.mongoTemplate.findOne(query, BasicInfoDTO.class, "healthArchive_basicInfo");
        return data;
    }
    
    public List<PhysicalExaminationDTO> getPhysicalExaminationList(PageParamDTO<MemberDTO> pageParamDto) throws Exception {

        Query query = null;
        if (pageParamDto.getOrderType().equals("1")) {
            if (pageParamDto.getOrderBy().equals("0")) {
                query = new Query(where("elderId").is(pageParamDto.getRequestData().getElderId())).with(new Sort(Sort.Direction.DESC, "updateDate"));
            } else if (pageParamDto.getOrderBy().equals("0")) {
                query = new Query(where("elderId").is(pageParamDto.getRequestData().getElderId())).with(new Sort(Sort.Direction.ASC, "updateDate"));
            }
        }

        long totalCount = this.mongoTemplate.count(query, "healthArchive_physicalExamination");
        PaginationDTO<PhysicalExaminationDTO> page = new PaginationDTO<>(Integer.parseInt(pageParamDto.getPageNo()),
                Integer.parseInt(pageParamDto.getPageSize()), totalCount);
        query.skip(page.getFirstResult());// skip相当于从那条记录开始
        query.limit(Integer.parseInt(pageParamDto.getPageSize()));// 从skip开始,取多少条记录
        List<PhysicalExaminationDTO> data = this.mongoTemplate.find(query, PhysicalExaminationDTO.class, "healthArchive_physicalExamination");
        return data;
    }

    public List<HealthAssessmentDTO> getHealthAssessmentList(PageParamDTO<MemberDTO> pageParamDto) throws Exception {
        Query query = null;
        if (pageParamDto.getOrderType().equals("1")) {
            if (pageParamDto.getOrderBy().equals("0")) {
                query = new Query(where("elderId").is(pageParamDto.getRequestData().getElderId())).with(new Sort(Sort.Direction.DESC, "updateDate"));
            } else if (pageParamDto.getOrderBy().equals("0")) {
                query = new Query(where("elderId").is(pageParamDto.getRequestData().getElderId())).with(new Sort(Sort.Direction.ASC, "updateDate"));
            }

        }

        long totalCount = this.mongoTemplate.count(query, "healthArchive_healthAssessment");
        PaginationDTO<HealthAssessmentDTO> page = new PaginationDTO<>(Integer.parseInt(pageParamDto.getPageNo()),
                Integer.parseInt(pageParamDto.getPageSize()), totalCount);
        query.skip(page.getFirstResult());// skip相当于从那条记录开始
        query.limit(Integer.parseInt(pageParamDto.getPageSize()));// 从skip开始,取多少条记录
        List<HealthAssessmentDTO> data = this.mongoTemplate.find(query, HealthAssessmentDTO.class, "healthArchive_healthAssessment");

        for (HealthAssessmentDTO healthAssessmentDto : data) {
            healthAssessmentDto.setUpdateDate(TimeUtils.formatTimeEight(healthAssessmentDto.getUpdateDate()));
        }

        return data;
    }

    public HealthAssessmentDTO getHealthAssessment(String healthAssessmentId, String keyId) throws Exception {

        Query query = new Query(where("healthAssessmentTemplateId").is(healthAssessmentId)).
                addCriteria(Criteria.where("_id").is(keyId));
        List<HealthAssessmentDTO> data = this.mongoTemplate.find(query, HealthAssessmentDTO.class, "healthArchive_healthAssessment");
        data.get(0).setUpdateDate(TimeUtils.formatTimeEight(data.get(0).getUpdateDate()));
        return data.get(0);
    }

    public List<HealthAssessmentTemplateDTO> GetHealthArchiveHealthAssessmentTemplateList(PageParamDTO pageParamDto) throws Exception {
        Query query = null;
        if (pageParamDto.getOrderType().equals("1")) {
            if (pageParamDto.getOrderBy().equals("0")) {
                query = new Query().with(new Sort(Sort.Direction.DESC, "updateDate"));
            } else if (pageParamDto.getOrderBy().equals("0")) {
                query = new Query().with(new Sort(Sort.Direction.ASC, "updateDate"));
            }

        }

        long totalCount = this.mongoTemplate.count(query, "healthArchive_healthAssessmentTemplate");
        PaginationDTO<HealthAssessmentTemplateDTO> page = new PaginationDTO<>(Integer.parseInt(pageParamDto.getPageNo()),
                Integer.parseInt(pageParamDto.getPageSize()), totalCount);
        query.skip(page.getFirstResult());// skip相当于从那条记录开始
        query.limit(Integer.parseInt(pageParamDto.getPageSize()));// 从skip开始,取多少条记录
        List<HealthAssessmentTemplateDTO> data = this.mongoTemplate.find(query, HealthAssessmentTemplateDTO.class, "healthArchive_healthAssessmentTemplate");

        for (HealthAssessmentTemplateDTO healthAssessmentTemplateDto : data) {
            healthAssessmentTemplateDto.setUpdateDate(TimeUtils.formatTimeEight(healthAssessmentTemplateDto.getUpdateDate()));
        }

        return data;
    }

    public List<PhysicalExaminationTemplateDTO> getPhysicalExaminationTemplateList(PageParamDTO pageParamDto) throws Exception {
        Query query = null;
        if (pageParamDto.getOrderType().equals("1")) {
            if (pageParamDto.getOrderBy().equals("0")) {
                query = new Query().with(new Sort(Sort.Direction.DESC, "updateDate"));
            } else if (pageParamDto.getOrderBy().equals("0")) {
                query = new Query().with(new Sort(Sort.Direction.ASC, "updateDate"));
            }

        }

        long totalCount = this.mongoTemplate.count(query, "healthArchive_physicalExaminationTemplate");
        PaginationDTO<PhysicalExaminationTemplateDTO> page = new PaginationDTO<>(Integer.parseInt(pageParamDto.getPageNo()),
                Integer.parseInt(pageParamDto.getPageSize()), totalCount);
        query.skip(page.getFirstResult());// skip相当于从那条记录开始
        query.limit(Integer.parseInt(pageParamDto.getPageSize()));// 从skip开始,取多少条记录
        List<PhysicalExaminationTemplateDTO> data = this.mongoTemplate.find(query, PhysicalExaminationTemplateDTO.class, "healthArchive_physicalExaminationTemplate");

        for (PhysicalExaminationTemplateDTO physicalExaminationTemplateDto : data) {
            physicalExaminationTemplateDto.setUpdateDate(TimeUtils.formatTimeEight(physicalExaminationTemplateDto.getUpdateDate()));
        }

        return data;
    }

    public PhysicalExaminationDTO getPhysicalExamination(String physicalExaminationId) throws Exception {
        Query query = new Query(where("physicalExaminationId").is(physicalExaminationId));
        List<PhysicalExaminationDTO> data = this.mongoTemplate.find(query, PhysicalExaminationDTO.class, "healthArchive_physicalExamination");
        return data.get(0);
    }

    public PhysicalExaminationDTO createPhysicalExamination(PhysicalExaminationDTO physicalExaminationDTO) throws Exception {

        physicalExaminationDTO.setPhysicalExaminationId(UUID.randomUUID().toString());
        physicalExaminationDTO.setUpdateDate(DateUtils.DateToStr(new Date(), "datetime"));
        try {
            this.mongoTemplate.insert(physicalExaminationDTO, "healthArchive_physicalExamination");
        } catch (Exception e) {
            throw new Exception("create healthServicePackage failure");
        }

        try {
            String message = userServiceClient.getEasemobMessageUrl("chatType2", physicalExaminationDTO.getPhysicalExaminationId());

            EasemobGroupDTO easemobGroupDTO = userServiceClient.getEasemobGroup(physicalExaminationDTO.getElderId());
            Runnable thread = new sendEasemobMessage(easemobGroupDTO.getEasemobGroupID(), message);
            threadExecutor.execute(thread);
        } catch (Exception e) {

        }

        return physicalExaminationDTO;
    }

    public void createHealthAssessment(HealthAssessmentDTO healthAssessmentAnswer) throws Exception {
        healthAssessmentAnswer.setUpdateDate(new Date());
        try {
            this.mongoTemplate.insert(healthAssessmentAnswer, "healthArchive_healthAssessment");
        } catch (Exception e) {
            throw new Exception("create healthServicePackage failure");
        }

    }


}
