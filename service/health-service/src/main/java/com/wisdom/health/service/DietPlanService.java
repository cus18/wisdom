package com.wisdom.health.service;

import com.wisdom.common.dto.health.DietPlanDTO;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zbm84 on 2017/5/27.
 */
@Service
@Transactional(readOnly = false)
public class DietPlanService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<DietPlanDTO> getAllDietPlan(String userid) {
        Query query=new Query().addCriteria(Criteria.where("elderUserID").is(userid));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC,"createDate")));
        return mongoTemplate.findAll(DietPlanDTO.class,"DietPlan");
    }

    public Integer insertDietPlan(DietPlanDTO dietPlanDTO) {
        dietPlanDTO.setId(UUIDUtil.getUUID());
        dietPlanDTO.setCreateDate(DateUtils.getDateTime());
        try {
            mongoTemplate.insert(dietPlanDTO,"DietPlan");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public DietPlanDTO getDietPlan(String dietPlanID) {
        Query query=new Query().addCriteria(Criteria.where("_id").is(dietPlanID));
        return mongoTemplate.findOne(query,DietPlanDTO.class,"DietPlan");
    }

    public List<DietPlanDTO> getAllDietPlanByDate(DietPlanDTO dietPlanDTO) {
        Query query=new Query().addCriteria(Criteria.where("elderUserID").is(dietPlanDTO.getElderUserID()))
                .addCriteria(Criteria.where("createDate").gte(dietPlanDTO.getStartDate()).andOperator(Criteria.where("createDate").lte(dietPlanDTO.getEndDate()+" 23:59")));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createDate")));
        List<DietPlanDTO> list=mongoTemplate.find(query,DietPlanDTO.class,"DietPlan");
        for (DietPlanDTO dietPlan:list) {
            dietPlan.setCreateTime(dietPlan.getCreateDate().split(" ")[1]);
            dietPlan.setCreateDate(dietPlan.getCreateDate().split(" ")[0]);
        }
        return list;
    }
}
