package com.wisdom.wechat.mapper;

import com.wisdom.common.dto.core.Page;
import com.wisdom.common.dto.community.course.*;
import com.wisdom.wechat.entity.WeChatAttention;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunxiao on 2017/8/8.
 */
@Repository
public interface WeChatAttentionMapper {

   Integer insertWeChatAttention(WeChatAttention weChatAttention);


}
