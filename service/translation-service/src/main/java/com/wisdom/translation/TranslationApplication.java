package com.wisdom.translation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@EnableFeignClients
@EnableScheduling
@MapperScan(basePackages = {"com.wisdom.translation.mapper"})
public class TranslationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TranslationApplication.class, args);
	}

}
