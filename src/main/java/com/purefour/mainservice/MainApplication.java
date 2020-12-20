package com.purefour.mainservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.purefour.mainservice")
@EnableFeignClients(basePackages = "com.purefour.mainservice.feign")
public class MainApplication {
	public static void main(String... args) {
		SpringApplication.run(MainApplication.class, args);
	}
}