package com.purefour.mainservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceInfo {

	@Value( "${spring.application.name}" )
	private String applicationName;

	@Value( "${spring.application.version}" )
	private String applicationVersion;

	@Bean
	public String getApiInfo() {
		return String.format("%s - Spring Java 15 - %s", applicationName, applicationVersion);
	}
}
