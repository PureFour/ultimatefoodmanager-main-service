package com.purefour.mainservice.feign;

import com.fasterxml.jackson.databind.JsonNode;
import com.purefour.mainservice.security.util.JwtUtil;
import feign.Feign;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "allegroAuthClient",
        url = "https://allegro.pl/auth/oauth/",
        fallback = AllegroAuthClient.AllegroClientFallback.class,
        configuration = AllegroAuthClient.AllegroApiClientConfiguration.class
)
public interface AllegroAuthClient {

    @Cacheable(value = "allegroApiTokenResponse", key="#apiKey")
    default String getClientToken(String apiKey) {
        final String authorizationHeader = "Basic " + apiKey;
        final JsonNode allegroApiTokenResponse = this.authenticate(authorizationHeader);
        return JwtUtil.BEARER + allegroApiTokenResponse.get("access_token").asText();
    }

    @PostMapping("token?grant_type=client_credentials")
    JsonNode authenticate(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);

    class AllegroClientFallback implements AllegroAuthClient {
        private static final String SERVICE_UNAVAILABLE_MSG = "AllegroAPI unavailable!";

        @Override
        public JsonNode authenticate(String authorizationHeader) {
            throw new IllegalStateException(SERVICE_UNAVAILABLE_MSG);
        }
    }

    class AllegroApiClientConfiguration {

        @Bean
        public Feign.Builder feignBuilder() {
            return Feign.builder()
                    .errorDecoder(new CommonErrorDecoder());
        }
    }
}
