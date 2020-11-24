package com.purefour.mainservice.config;

import java.util.function.Predicate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableAsync
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder()
                    .title("UltimateFoodManager API")
                    .description("A Spring API provided by master developer ~Daniel")
                    .version("0.0.1")
                    .build())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicate.not(PathSelectors.regex("/error.*")))
                .paths(Predicate.not(PathSelectors.regex("/admin.*")))
                .paths(Predicate.not(PathSelectors.regex("/actuator.*")))
                .build();
    }
}