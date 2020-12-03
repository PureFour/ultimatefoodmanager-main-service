package com.purefour.mainservice.feign;

import com.fasterxml.jackson.databind.JsonNode;
import feign.Feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
	name = "allegroAuthClient",
	url = "https://allegro.pl.allegrosandbox.pl/auth/oauth/",
	fallback = AllegroApiAuthClient.AllegroApiClientFallback.class,
	configuration = AllegroApiAuthClient.AllegroApiClientConfiguration.class
)
public interface AllegroApiAuthClient {

	@PostMapping("token?grant_type=client_credentials")
	JsonNode authenticate(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);

	class AllegroApiClientFallback implements AllegroApiAuthClient {
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
				.errorDecoder(new DatabaseClientErrorDecoder()); // TODO wyciagnac do commonDecodera
		}
	}
}
