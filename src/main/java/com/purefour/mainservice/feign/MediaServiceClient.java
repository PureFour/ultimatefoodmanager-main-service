package com.purefour.mainservice.feign;

import com.purefour.mainservice.feign.decoder.CommonErrorDecoder;
import feign.Feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "media-service",
        url = "${media-service.protocol}://${media-service.host}:${media-service.port}",
        fallback = MediaServiceClient.MediaServiceClientFallback.class,
        configuration = MediaServiceClient.MediaServiceClientConfiguration.class
)
public interface MediaServiceClient {

    @GetMapping("helloWorld")
    String helloWorld(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);

    class MediaServiceClientFallback implements MediaServiceClient {
        private static final String SERVICE_UNAVAILABLE_MSG = "MediaService unavailable.";

        @Override
        public String helloWorld(String authorizationHeader) {
            throw new IllegalStateException(SERVICE_UNAVAILABLE_MSG);
        }
    }

    class MediaServiceClientConfiguration {
        @Bean
        public Feign.Builder feignBuilder() {
            return Feign.builder()
                    .errorDecoder(new CommonErrorDecoder());
        }
    }
}
