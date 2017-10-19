package com.wisdom.community.service;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.core.DictDTO;
import com.wisdom.common.dto.core.Page;
import com.wisdom.common.dto.core.PageParamDTO;
import com.wisdom.common.dto.community.course.*;
import com.wisdom.common.dto.core.ResponseDTO;
import com.wisdom.common.util.StringUtils;
import com.wisdom.community.client.CoreServiceClient;
import com.wisdom.community.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunxiao on 2017/8/8.
 */

@Service
@Transactional(readOnly = false)
public class LiveCourseService{

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CoreServiceClient coreServiceClient;

    public Page getLiveCourseByInfo(PageParamDTO<String> pageParamDTO, String[] status) {
        LiveCourseDTO liveCourseDTO = new LiveCourseDTO();
        liveCourseDTO.setLiveCourseStatusArray(status);
        Page<LiveCourseDTO> page = new Page(Integer.parseInt(pageParamDTO.getPageNo()),Integer.parseInt(pageParamDTO.getPageSize()));
        Page<LiveCourseDTO> p = courseMapper.getLiveCourseByInfo(liveCourseDTO,page);
        return p;
    }

    public List<LiveCourseDTO> getAllLiveCourseByInfo(String[] status) {
        LiveCourseDTO liveCourseDTO = new LiveCourseDTO();
        liveCourseDTO.setLiveCourseStatusArray(status);
        return courseMapper.getAllLiveCourseByInfo(liveCourseDTO);
    }

    public LiveCourseDTO getLiveBroadCastDetail(String elderId,LiveCourseDTO dto) {
        dto = courseMapper.getLiveBroadCastDetail(dto);
        LiveCourseRegisterDTO liveCourseRegisterDTO = new LiveCourseRegisterDTO();
        if(dto != null){
            liveCourseRegisterDTO.setLiveCourseId(dto.getLiveCourseId());
            liveCourseRegisterDTO.setElderId(elderId);
            liveCourseRegisterDTO = courseMapper.getLiveCourseRegister(liveCourseRegisterDTO);
        }
        dto.setLiveCourseRegisterStatus(liveCourseRegisterDTO != null?"yes":"no");
        return dto;
    }

    public void registerLiveBroadCast(LiveCourseRegisterDTO dto) {
        courseMapper.saveLiveCourseRegister(dto);
    }

    public List<OnlineCourseDTO> getOnlineCourseList(PageParamDTO<String> pageParamDTO) {
        List<OnlineCourseDTO> returnList = new ArrayList<>();
        if("page".equals(pageParamDTO.getRequestData())){
            Page page = new Page(Integer.parseInt(pageParamDTO.getPageNo()),Integer.parseInt(pageParamDTO.getPageSize()));
            OnlineCourseDTO onlineCourseDTO = new OnlineCourseDTO();
            returnList = courseMapper.getOnlineCourseList(onlineCourseDTO,page).getList();
        }else{
            if(StringUtils.isNotNull(pageParamDTO.getRequestData())){
                DictDTO dict = new DictDTO();
                dict.setType("video");
                dict.setValue(pageParamDTO.getRequestData());
                ResponseDTO<List<DictDTO>> dictListValue = coreServiceClient.findDictListByInfo(dict);
                if(dictListValue.getResult().equals(StatusConstant.SUCCESS)){
                    if(dictListValue.getResponseData()!=null && dictListValue.getResponseData().size()>0)
                    {
                        List labelIdList = new ArrayList();
                        for(DictDTO dictVal : dictListValue.getResponseData())
                        {
                            labelIdList.add(dictVal.getId());
                        }
                        OnlineCourseDTO onlineCourseDTO = new OnlineCourseDTO();
                        onlineCourseDTO.setOnlineCourseLabelIds(labelIdList);
                        returnList = courseMapper.getAllOnlineCourseListByInfo(onlineCourseDTO);

                    }
                }

//                if(dictList1!=null && dictList1.size()>0){
//                    List lableIdList = new ArrayList();
//                    dict.setValue("");
//                    dict.setParentId(dictList1.get(0).getId());
//                    List<DictDTO> dictList2 = coreServiceClient.findDictListByInfo(dict);
//                    if(dictList2!=null && dictList2.size()>0){
//                        for(DictDTO temp : dictList2){
//                            lableIdList.add(temp.getId());
//                        }
//                    }else{
//                        for(DictDTO temp : dictList1){
//                            lableIdList.add(temp.getId());
//                        }
//                    }
//                    OnlineCourseDTO onlineCourseDTO = new OnlineCourseDTO();
//                    onlineCourseDTO.setOnlineCourseLabelIds(lableIdList);
//                    returnList = courseMapper.getAllOnlineCourseListByInfo(onlineCourseDTO);
//                }
            }else{
                OnlineCourseDTO onlineCourseDTO = new OnlineCourseDTO();
                returnList = courseMapper.getAllOnlineCourseListByInfo(onlineCourseDTO);
            }
        }
        return returnList;
    }

    public OnlineCourseDTO getOnlineCourseDetail(OnlineCourseDTO onlineCourseDTO) {
        OnlineCourseDTO result = courseMapper.getOnlineCourse(onlineCourseDTO);
        OnlineCourseDataDTO onlineCourseDataDTO = new OnlineCourseDataDTO();
        onlineCourseDataDTO.setOnlineCourseId(onlineCourseDTO.getOnlineCourseId());
        List dataList = courseMapper.getOnlineCourseDataList(onlineCourseDataDTO);
        result.setOnLineCourseDataDTOList(dataList);
        return result;
    }

    public List<OnlineCourseDiscussDTO> getOnlineCourseDiscuss(PageParamDTO<String> pageParamDTO) {
        OnlineCourseDiscussDTO dto = new OnlineCourseDiscussDTO();
        dto.setOnlineCourseId(Integer.parseInt(pageParamDTO.getRequestData()));
        Page page = new Page(Integer.parseInt(pageParamDTO.getPageNo()),Integer.parseInt(pageParamDTO.getPageSize()));
        return courseMapper.getOnlineCourseDiscuss(dto,page);
    }

    public void createOnlineCourseDiscuss(OnlineCourseDiscussDTO onlineCourseDiscussDTO) {
        courseMapper.addOnlineCourseDiscuss(onlineCourseDiscussDTO);
    }

    public void updateLiveCourse(LiveCourseDTO liveCourseDTO) {
        courseMapper.updateLiveCourse(liveCourseDTO);
    }

    public List<OnlineCourseMyCourseDTO> getMyOnlineCourse(OnlineCourseMyCourseDTO dto, PageParamDTO<String> pageParamDTO) {
        if(pageParamDTO.getRequestData().indexOf(",")>0){
            Page<OnlineCourseMyCourseDTO> page = new Page(Integer.parseInt(pageParamDTO.getPageNo()),Integer.parseInt(pageParamDTO.getPageSize()));
            dto.setType(pageParamDTO.getRequestData().split(",")[1]);
            Page<OnlineCourseMyCourseDTO> p = courseMapper.getMyOnlineCourse(dto,page);
            for(OnlineCourseMyCourseDTO temp : p.getList()){
                OnlineCourseDTO onlineCourseDTO = new OnlineCourseDTO();
                onlineCourseDTO.setOnlineCourseId(temp.getCourseId());
                temp.setOnlineCourseDTO(courseMapper.getOnlineCourse(onlineCourseDTO));
            }
            return p.getList();
        }else{
            dto.setType(pageParamDTO.getRequestData());
            List<OnlineCourseMyCourseDTO> list = courseMapper.getAllMyOnlineCourseByInfo(dto);
            for(OnlineCourseMyCourseDTO temp : list){
                OnlineCourseDTO onlineCourseDTO = new OnlineCourseDTO();
                onlineCourseDTO.setOnlineCourseId(temp.getCourseId());
                temp.setOnlineCourseDTO(courseMapper.getOnlineCourse(onlineCourseDTO));
            }
            return list;
        }
    }
}
