package com.purefour.mainservice.feign;

import com.purefour.mainservice.feign.decoder.CommonErrorDecoder;
import com.purefour.mainservice.model.product.ImageModel;
import feign.Feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(
        name = "media-service",
        url = "${media-service.protocol}://${media-service.host}:${media-service.port}",
        fallback = MediaServiceClient.MediaServiceClientFallback.class,
        configuration = MediaServiceClient.MediaServiceClientConfiguration.class
)
public interface MediaServiceClient {

    //TODO tutaj lepiej zrobić wrzucanie ImageModel!

    @PostMapping("images/upload")
    ImageModel saveImage(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, MultipartFile imageFile);

    @GetMapping("images/{uuid}")
    ImageModel getImage(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable("uuid") String uuid);

    @DeleteMapping("images/{uuid}")
    String deleteImage(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable("uuid") String uuid);

    class MediaServiceClientFallback implements MediaServiceClient {
        private static final String SERVICE_UNAVAILABLE_MSG = "MediaService unavailable.";

        @Override
        public ImageModel saveImage(String authorizationHeader, MultipartFile imageFile) {
            throw new IllegalStateException(SERVICE_UNAVAILABLE_MSG);
        }

        @Override
        public ImageModel getImage(String authorizationHeader, String uuid) {
            throw new IllegalStateException(SERVICE_UNAVAILABLE_MSG);
        }

        @Override
        public String deleteImage(String authorizationHeader, String uuid) {
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
