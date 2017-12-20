package com.wisdom.living.mapper;

import com.wisdom.living.entity.LivingServiceOrder;
import com.wisdom.living.entity.LivingServiceOrderStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zbm84 on 2017/12/4.
 */
@Repository
public interface LivingServiceOrderMapper {

    Integer insertLivingServiceOrder(LivingServiceOrder livingServiceOrder);

    List<LivingServiceOrderStatus> getLivingServiceOrderStatus(@Param("openID")String openID,@Param("status")String status);

    LivingServiceOrder getLivingServiceOrder(@Param("id")String id);

    Integer delLivingServiceOrder(@Param("id")String livingServiceOrderID);
}
