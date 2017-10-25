package com.wisdom.core.service;


import com.wisdom.common.dto.basic.BannerDTO;
import com.wisdom.core.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class BannerService {

    @Autowired
    BannerMapper bannerMapper;

    public List<BannerDTO> getBannerList() {
        return bannerMapper.getBannerList();
    }

}
