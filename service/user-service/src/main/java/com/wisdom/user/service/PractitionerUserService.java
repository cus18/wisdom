package com.wisdom.user.service;

import com.wisdom.common.dto.userService.PractitionerUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class PractitionerUserService {

    @Autowired
    RedisService redisService;

    public boolean updateSysPractitionerUser(PractitionerUserDTO practitionerUserInfo) {
        return true;
    }
}
