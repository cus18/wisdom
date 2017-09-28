package com.wisdom.registry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RefreshScope
public class RegistryApplication {

	@Value("${env.name:World!}") String name ;

	@RequestMapping("/app")
	public String home(){
		return "Hello " + name;
	}

	public static void main(String[] args) {
		SpringApplication.run(RegistryApplication.class, args);
	}

}
