package com.wisdom.community.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("health-service")
public interface HealthServiceClient {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    String healthServiceTest();

}
