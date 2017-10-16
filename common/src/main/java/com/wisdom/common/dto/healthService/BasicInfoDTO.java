package com.wisdom.common.dto.healthService;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

public class BasicInfoDTO {

    @JSONField(name = "healthArchiveId")
    private String healthArchiveId;

    @JSONField(name = "memberCarID")
    private String memberCarID;

    @JSONField(name = "elderName")
    private String elderName;

    @JSONField(name = "elderId")
    private String elderId;

    @JSONField(name = "gender")
    private String gender;

    @JSONField(name = "birthday")
    private Date birthday;

    @JSONField(name = "idNum")
    private String idNum;

    @JSONField(name = "phone")
    private String phone;

    @JSONField(name = "workPlace")
    private String workPlace;

    @JSONField(name = "nation")
    private String nation;

    @JSONField(name = "education")
    private String education;

    @JSONField(name = "career")
    private String career;

    @JSONField(name = "marriage")
    private String marriage;

    @JSONField(name = "costWay")
    private String costWay;

    @JSONField(name = "aboBloodType")
    private String aboBloodType;

    @JSONField(name = "rhBloodType")
    private String rhBloodType;

    @JSONField(name = "allergicHistory")
    private String allergicHistory;

    @JSONField(name = "exposureHistory")
    private String exposureHistory;

    @JSONField(name = "pastHistory")
    private String pastHistory;

    @JSONField(name = "familyHistory")
    private String familyHistory;

    @JSONField(name = "livestock")
    private String livestock;

    private String healthServiceName;

    @JSONField(name = "age")
    private String age;

    private String doctorName;

    private String nurseName;

    @JSONField(name = "relativeList")
    private List<RelativeDTO> relativeList;

    public String getHealthServiceName() {
        return healthServiceName;
    }

    public void setHealthServiceName(String healthServiceName) {
        this.healthServiceName = healthServiceName;
    }

    public String getHealthArchiveId() {
        return healthArchiveId;
    }

    public void setHealthArchiveId(String healthArchiveId) {
        this.healthArchiveId = healthArchiveId;
    }

    public String getPastHistory() {
        return pastHistory;
    }

    public void setPastHistory(String pastHistory) {
        this.pastHistory = pastHistory;
    }

    public String getElderName() {
        return elderName;
    }

    public void setElderName(String elderName) {
        this.elderName = elderName;
    }

    public String getElderId() {
        return elderId;
    }

    public void setElderId(String elderId) {
        this.elderId = elderId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getCostWay() {
        return costWay;
    }

    public void setCostWay(String costWay) {
        this.costWay = costWay;
    }

    public String getAboBloodType() {
        return aboBloodType;
    }

    public void setAboBloodType(String aboBloodType) {
        this.aboBloodType = aboBloodType;
    }

    public String getRhBloodType() {
        return rhBloodType;
    }

    public void setRhBloodType(String rhBloodType) {
        this.rhBloodType = rhBloodType;
    }

    public String getAllergicHistory() {
        return allergicHistory;
    }

    public void setAllergicHistory(String allergicHistory) {
        this.allergicHistory = allergicHistory;
    }


    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }


    public String getLivestock() {
        return livestock;
    }

    public void setLivestock(String livestock) {
        this.livestock = livestock;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMemberCarID() {
        return memberCarID;
    }

    public void setMemberCarID(String memberCarID) {
        this.memberCarID = memberCarID;
    }

    public String getExposureHistory() {
        return exposureHistory;
    }

    public void setExposureHistory(String exposureHistory) {
        this.exposureHistory = exposureHistory;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public List<RelativeDTO> getRelativeList() {
        return relativeList;
    }

    public void setRelativeList(List<RelativeDTO> relativeList) {
        this.relativeList = relativeList;
    }


}