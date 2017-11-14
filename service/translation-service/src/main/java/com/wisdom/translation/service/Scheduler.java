package com.wisdom.translation.service;

import com.google.gson.Gson;
import com.wisdom.translation.conf.RedisConfig;
import com.wisdom.translation.util.HttpTool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class Scheduler {

    private static Boolean loadStartUp = true;

    @Autowired
    RedisService redisService;

    private static Logger logger = Logger.getLogger(RedisConfig.class);

    @Scheduled(cron="0 0/21600 * * * ?") //每15天执行一次
    public void recognitionTokenRefresh() {
        logger.info("每20天执行一次。开始……");

        Map header = new HashMap<>();
        header.put("Content-Type","application/json");
        String url = "https://openapi.baidu.com/oauth/2.0/token?" +
                "grant_type=client_credentials&" +
                "client_id="+ "YetN22tgDzZgtlBDqZNN52pF" +
                "&client_secret=" + "q5aTaUc7X2uGGf7Ct9XsEKGjOzoRPXDj";

        String str = null;
        try {
            str = HttpTool.Instance.doGet(url,header);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        redisService.expire("recognitionToken",0);
        Map res = new Gson().fromJson(str,Map.class);
        String token = (String) res.get("access_token");
        redisService.set("recognitionToken",token);

        logger.info("每15天执行一次。结束。");
    }

    //开机执行
    @Scheduled(fixedRate=2000)
    public void recognitionTokenCreate() {
        if(loadStartUp)
        {
            recognitionTokenRefresh();
            loadStartUp = false;
        }
    }

}