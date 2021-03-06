package com.wisdom.living.mapper;

import com.wisdom.living.entity.LivingService;
import com.wisdom.living.entity.LivingServiceOffice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivingMapper {


     List<LivingService> getLivingService(LivingService livingService);

    Integer updateLivingService(LivingService livingService);

    List<LivingServiceOffice> getLivingServiceOffice(LivingServiceOffice livingServiceOffice);

    String getLivingServiceOfficeCount(@Param("sysOfficeID")String sysOfficeID);

}
