package com.wisdom.core.service;

import com.google.gson.Gson;
import com.wisdom.common.dto.core.DictDTO;
import com.wisdom.common.dto.core.user.RelativeElderDTO;
import com.wisdom.common.dto.core.user.UserInfoDTO;
import com.wisdom.core.mapper.DictMapper;
import com.wisdom.core.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class DictService {

    @Autowired
    DictMapper dictMapper;

    public List<DictDTO> findDictListByInfo(DictDTO dictDTO)
    {
        return  dictMapper.findListByInfo(dictDTO);
    }

}
