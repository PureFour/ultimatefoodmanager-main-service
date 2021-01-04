package com.purefour.mainservice.feign;

import com.fasterxml.jackson.databind.JsonNode;
import com.purefour.mainservice.feign.decoder.CommonErrorDecoder;
import feign.Feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "allegroApiClient",
        url = "https://api.allegro.pl/",
        fallback = AllegroApiClient.AllegroApiClientFallback.class,
        configuration = AllegroApiClient.AllegroApiClientConfiguration.class
)
public interface AllegroApiClient {

    String ACCEPT_MEDIA_TYPE_HEADER = "application/vnd.allegro.public.v1+json";
    String SUPERMARKET_CATEGORY_ID = "261675";
    String PRICE_ASCENDING_SORT = "+price";

    default JsonNode getProductByBarcode(String authorizationHeader, String barcode) {
        return this.getProduct(authorizationHeader, ACCEPT_MEDIA_TYPE_HEADER, barcode, SUPERMARKET_CATEGORY_ID, PRICE_ASCENDING_SORT);
    }

    @GetMapping("offers/listing")
    JsonNode getProduct(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestHeader(HttpHeaders.ACCEPT) String acceptMediaTypeHeader,
            @RequestParam("phrase") String phrase,
            @RequestParam("category.id") String categoryId,
            @RequestParam("sort") String sort);

    class AllegroApiClientFallback implements AllegroApiClient {
        private static final String SERVICE_UNAVAILABLE_MSG = "AllegroAPI unavailable!";

        @Override
        public JsonNode getProduct(
                String authorizationHeader,
                String acceptMediaTypeHeader,
                String phrase,
                String categoryId,
                String sort) {
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
