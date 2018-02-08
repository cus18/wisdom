package com.wisdom.health.service;

import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.common.dto.health.*;
import com.wisdom.common.dto.core.user.EasemobGroupDTO;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.StringUtils;
import com.wisdom.health.client.CoreServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sunxiao on 2017/5/11.
 */
@Service
@Transactional(readOnly = false)
public class HealthDataService {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    CoreServiceClient CoreServiceClient;

    private static ExecutorService threadExecutor = Executors.newSingleThreadExecutor();

    public void sendEasemobMessage(String easemobGroup,String message) {
        CoreServiceClient.sendEasemobMessage(easemobGroup,message);
    }

    public class sendEasemobMessage extends Thread {

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

    public ResponseDTO<DetectionDTO> getHealthDataFromMongo(String detectionType, String detectionDateType, String elderId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateNow = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(dateNow);
        if(detectionDateType.equals("day"))
        {
            cl.add(Calendar.DAY_OF_YEAR, -1);
        }else if(detectionDateType.equals("week"))
        {
            cl.add(Calendar.WEEK_OF_YEAR, -1);
        }else if(detectionDateType.equals("month")){
            cl.add(Calendar.MONTH, -1);
        }
        else if(detectionDateType.equals("threeMonth")){
            cl.add(Calendar.MONTH, -3);
        }
        else if(detectionDateType.equals("halfYear")){
            cl.add(Calendar.MONTH, -6);
        }
        Date dateFrom = cl.getTime();
        Query query = new Query().addCriteria(Criteria.where("elderId").is(elderId))
                .addCriteria(Criteria.where("type").is(detectionType))
                .addCriteria(Criteria.where("measureTime").gte(sdf.format(dateFrom))
                        .andOperator(Criteria.where("measureTime").lte(sdf.format(dateNow))));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "measureTime")));
        List<HealthDataDTO> data = mongoTemplate.find(query, HealthDataDTO.class,"finalHealthData");

        ResponseDTO<DetectionDTO> responseDTO = new ResponseDTO<>();
        DetectionDTO detectionDTO = new DetectionDTO();
        detectionDTO.setElderId(elderId);
        detectionDTO.setDetectionType(detectionDateType);
        List detectionDataList = new ArrayList<>();
        for(HealthDataDTO healthData : data)
        {
            detectionDataList.add(healthData.getData());
        }
        detectionDTO.setDetectionData(detectionDataList);
        responseDTO.setResponseData(detectionDTO);
        return responseDTO;
    }

    public ResponseDTO<List> getTestReport(String elderId , String startDate, String endDate) {
        ResponseDTO<List> responseDTO = new ResponseDTO<List>();
        Query query = new Query().addCriteria(Criteria.where("elderId").is(elderId))
                .addCriteria(Criteria.where("testDate").gte(startDate)
                        .andOperator(Criteria.where("testDate").lte(endDate)));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "testDate"))).with(new Sort(new Sort.Order(Sort.Direction.DESC, "testTime")));
        List<TestReportDTO> data = mongoTemplate.find(query, TestReportDTO.class, "testReport");
        responseDTO.setResponseData(data);
        return responseDTO;
    }

    public ResponseDTO<List> getTreatmentRecord(String elderId , String startDate, String endDate) {
        ResponseDTO<List> responseDTO = new ResponseDTO<List>();
        Query query = new Query().addCriteria(Criteria.where("elderId").is(elderId))
                .addCriteria(Criteria.where("recordDate").gte(startDate+" 00:00")
                        .andOperator(Criteria.where("recordDate").lte(endDate+" 23:59")));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "recordDate")));
        List<TreatmentRecordDTO> data = mongoTemplate.find(query, TreatmentRecordDTO.class, "treatmentRecord");
        responseDTO.setResponseData(data);
        return responseDTO;
    }

    public void saveHealthDataToMongo(DetectionDTO detectionDTO) throws Exception {
        HealthDataDTO vo = new HealthDataDTO();
        vo.setElderId(detectionDTO.getElderId());
        vo.setPname(detectionDTO.getElderName());
        vo.setType(detectionDTO.getDetectionType());
        String measureTime = ((CommonDataDTO)detectionDTO.getDetectionData().get(0)).getMeasureTime();
        if(DateUtils.StrToDate(measureTime,"yyyy-MM-dd HH:mm").getTime() > new Date().getTime()){
            throw new Exception("测量时间不能晚于当前时间！");
        }
        vo.setMeasureTime(measureTime);
        vo.setMeasureId(UUID.randomUUID().toString());
        String message = "";
        if("bg".equals(vo.getType())){
            BloodSugarDataDTO info = new BloodSugarDataDTO();
            info.setResult(((CommonDataDTO)detectionDTO.getDetectionData().get(0)).getBgValue());
            info.setMealType(((CommonDataDTO)detectionDTO.getDetectionData().get(0)).getMealType());
            info.setMeasureTime(measureTime);
            info.setRemark(((CommonDataDTO)detectionDTO.getDetectionData().get(0)).getRemarks());
            updateData(vo,info,vo.getType());
            vo.setData(info);
            //'/bloodSugarRecord/:bloodSugarNum,:recorded,:timeType,:timeDate,:readOnly
            message = CoreServiceClient.getEasemobMessageUrl("chatType5",vo.getMeasureId(),info.getResult(),info.getMealType(),vo.getMeasureTime());
        }else if("bp".equals(vo.getType())){
            BloodPressureDataDTO info = new BloodPressureDataDTO();
            info.setSystolic(((CommonDataDTO)detectionDTO.getDetectionData().get(0)).getSystolic());
            info.setDiastolic(((CommonDataDTO)detectionDTO.getDetectionData().get(0)).getDiastolic());
            info.setHeartRate(((CommonDataDTO)detectionDTO.getDetectionData().get(0)).getHeartRate());
            info.setMeasureTime(measureTime);
            info.setRemark(((CommonDataDTO)detectionDTO.getDetectionData().get(0)).getRemarks());
            vo.setData(info);
            message = CoreServiceClient.getEasemobMessageUrl("chatType4",vo.getMeasureId(),vo.getMeasureTime(),info.getDiastolic(),info.getSystolic(),info.getHeartRate());
        }else{
            throw new Exception("类型不存在！");
        }
        mongoTemplate.save(vo,"finalHealthDataDTO");
        mongoTemplate.save(vo);

        if(StringUtils.isNotNull(message)){
            EasemobGroupDTO easemobGroup = CoreServiceClient.getEasemobGroup(detectionDTO.getElderId());
            Runnable thread = new sendEasemobMessage(easemobGroup.getEasemobGroupID(),message);
            threadExecutor.execute(thread);
        }
    }
    
    public void saveControlTargetToMongo(DetectionDTO detectionDTO) {
        ControlTargetDataDTO ct = (ControlTargetDataDTO)detectionDTO.getDetectionData().get(0);
        ct.setElderId(detectionDTO.getElderId());
        ct.setElderName(detectionDTO.getElderName());
        ct.setType(detectionDTO.getDetectionType());
        Query query = new Query().addCriteria(Criteria.where("elderId").is(ct.getElderId()))
                .addCriteria(Criteria.where("type").is(ct.getType()));
        mongoTemplate.remove(query,ct.getClass());
        mongoTemplate.save(ct);
    }

    public DetectionDTO getControlTargetFromMongo(String elderId, String detectionType) {
        Query query = new Query().addCriteria(Criteria.where("elderId").is(elderId))
                .addCriteria(Criteria.where("type").is(detectionType));
        List list = mongoTemplate.find(query,ControlTargetDataDTO.class);
        DetectionDTO detectionDTO = new DetectionDTO();
        detectionDTO.setDetectionData(list);
        return detectionDTO;
    }
    
    public void createTestReport(TestReportDTO testReportDTO) throws Exception {
        TestReportDTO testReport = new TestReportDTO();
        testReport.setReportId(UUID.randomUUID().toString());
        testReport.setElderId(testReportDTO.getElderId());
        testReport.setElderName(testReportDTO.getElderName());
        testReport.setProviderId(testReportDTO.getProviderId());
        testReport.setProviderName(testReportDTO.getProviderName());
        testReport.setTestDate(DateUtils.getDate("yyyy-MM-dd"));
        testReport.setTestTime(DateUtils.getDate("HH:mm:ss"));
        testReport.setTestPicUrl(testReportDTO.getTestPicUrl());
        testReport.setDescription(testReportDTO.getDescription());
        try{
            mongoTemplate.save(testReport);
        }catch (Exception e){
            throw new Exception("createTestReportDTO failure");
        }

        String message = CoreServiceClient.getEasemobMessageUrl("chatType6",testReport.getReportId(),testReport.getTestDate(),testReport.getTestTime());

        EasemobGroupDTO easemobGroup = CoreServiceClient.getEasemobGroup(testReportDTO.getElderId());
        Runnable thread = new sendEasemobMessage(easemobGroup.getEasemobGroupID(),message);
        threadExecutor.execute(thread);
    }

    public void createTreatment(TreatmentDTO treatmentDTO) throws Exception {
        TreatmentRecordDTO treatmentRecord = new TreatmentRecordDTO();
        treatmentRecord.setTreatmentId(UUID.randomUUID().toString());
        treatmentRecord.setElderId(treatmentDTO.getElderId());
        treatmentRecord.setElderName(treatmentDTO.getElderName());
        treatmentRecord.setProviderId(treatmentDTO.getProviderId());
        treatmentRecord.setProviderName(treatmentDTO.getProviderName());
        treatmentRecord.setRecordDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        treatmentRecord.setAudioUrl(treatmentDTO.getTreatmentAudio());
        treatmentRecord.setDescription(treatmentDTO.getDescription());
        try{
            mongoTemplate.save(treatmentRecord);
        }catch (Exception e){
            throw new Exception("createTestReportDTO failure");
        }

        String message = CoreServiceClient.getEasemobMessageUrl("chatType7",treatmentRecord.getTreatmentId(),treatmentRecord.getRecordDate());
        EasemobGroupDTO easemobGroup = CoreServiceClient.getEasemobGroup(treatmentDTO.getElderId());
        Runnable thread = new sendEasemobMessage(easemobGroup.getEasemobGroupID(),message);
        threadExecutor.execute(thread);
    }

    private void updateData(HealthDataDTO vo , BloodSugarDataDTO info, String type){
        Query query = null;
        List<MeasurementRuleDTO> rule = null;
        query = new Query().addCriteria(Criteria.where("elderId").is(vo.getElderId()))
                .addCriteria(Criteria.where("type").is(type));
        rule = mongoTemplate.find(query, MeasurementRuleDTO.class);
        if(rule==null || rule.size()==0){
            query = new Query().addCriteria(Criteria.where("elderId").is("bloodSugarDefaultRole"))
                    .addCriteria(Criteria.where("type").is(type));
            rule = mongoTemplate.find(query, MeasurementRuleDTO.class);
        }
        if(rule!=null && rule.size()>0){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            BloodSugarRoleDTO role = ((BloodSugarRoleDTO)rule.get(0).getRule());
            Field[] fields = BloodSugarRoleDTO.class.getDeclaredFields();
            for(Field field:fields){
                String fieldName = field.getName();
                try {
                    Method m = role.getClass().getMethod(
                            "get" + fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length()));
                    String value = String.valueOf(m.invoke(role));
                    if(value!=null && !"".equals(value)){
                        String[] time = value.split("-");
                        String elderId = vo.getElderId();
                        String measureTime = vo.getMeasureTime();
                        if(sdf.parse(measureTime).getTime()>=sdf.parse(measureTime.split(" ")[0]+" "+time[0]).getTime() && sdf.parse(measureTime).getTime()<sdf.parse(measureTime.split(" ")[0]+" "+time[1]).getTime()){
                            info.setPeriod(fieldName);
                            vo.setData(info);
                            query = new Query().addCriteria(Criteria.where("elderId").is(elderId))
                                    .addCriteria(Criteria.where("type").is(type))
                                    .addCriteria(Criteria.where("measureTime").gte(measureTime.split(" ")[0]+" "+time[0])
                                            .andOperator(Criteria.where("measureTime").lte(measureTime.split(" ")[0]+" "+time[1])));
                            List<HealthDataDTO> data = mongoTemplate.find(query, HealthDataDTO.class,"finalHealthDataDTO");
                            if(data!=null&&data.size()>0){
                                for(HealthDataDTO temp:data){
                                    if(sdf.parse(temp.getMeasureTime()).getTime()<sdf.parse(measureTime).getTime()){
                                        mongoTemplate.remove(query,HealthDataDTO.class,"finalHealthDataDTO");
                                        mongoTemplate.save(vo,"finalHealthDataDTO");
                                    }
                                }
                            }else{
                                mongoTemplate.save(vo,"finalHealthDataDTO");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public TestReportDTO getSingleTestReportFromMongo(String id) {
        Query query = new Query().addCriteria(Criteria.where("reportId").is(id));
        List<TestReportDTO> data = mongoTemplate.find(query, TestReportDTO.class, "testReport");
        TestReportDTO testReportDTO = new TestReportDTO();
        testReportDTO.setElderId(data.get(0).getElderId());
        testReportDTO.setDescription(data.get(0).getDescription());
        testReportDTO.setElderName(data.get(0).getElderName());
        testReportDTO.setProviderId(data.get(0).getProviderId());
        testReportDTO.setProviderName(data.get(0).getProviderName());
        testReportDTO.setReportId(data.get(0).getReportId());
        testReportDTO.setTestPicUrl(data.get(0).getTestPicUrl());
        testReportDTO.setTestDate(data.get(0).getTestDate());
        return testReportDTO;
    }

    public TreatmentDTO getSingleTreatmentRecordFromMongo(String id) {
        Query query = new Query().addCriteria(Criteria.where("treatmentId").is(id));
        List<TreatmentRecordDTO> data = mongoTemplate.find(query, TreatmentRecordDTO.class, "treatmentRecord");
        TreatmentDTO treatmentDTO = new TreatmentDTO();
        treatmentDTO.setUpdateDate(data.get(0).getRecordDate());
        treatmentDTO.setProviderName(data.get(0).getProviderName());
        treatmentDTO.setProviderId(data.get(0).getProviderId());
        treatmentDTO.setElderName(data.get(0).getElderName());
        treatmentDTO.setElderId(data.get(0).getElderId());
        treatmentDTO.setTreatmentAudio(data.get(0).getAudioUrl());
        treatmentDTO.setTreatmentData(data.get(0).getRecordDate());
        treatmentDTO.setTreatmentId(data.get(0).getTreatmentId());
        return treatmentDTO;
    }

    public HealthDataDTO getSingleHealthDataFromMongo(String id) {
        Query query = new Query().addCriteria(Criteria.where("measureId").is(id));
        List<HealthDataDTO> data = mongoTemplate.find(query, HealthDataDTO.class,"finalHealthDataDTO");
        return data.get(0);
    }

}
