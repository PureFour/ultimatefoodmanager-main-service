package com.purefour.mainservice.feign;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@ConditionalOnProperty(
	name = "openFoodFactsApi",
	havingValue = "real")
@FeignClient(
	name = "openFoodFactsApi",
	url = "https://world.openfoodfacts.org/",
	fallback = OpenFoodFactsRealClient.OpenFoodFactsRealClientFallback.class
)
public interface OpenFoodFactsRealClient extends OpenFoodFactsClient {

	@GetMapping("api/v0/product/{barcode}.json")
	JsonNode getProduct(@PathVariable("barcode") String barcode, @RequestHeader("User-Agent") String userAgentHeader);

	class OpenFoodFactsRealClientFallback implements OpenFoodFactsRealClient {
		private static final String SERVICE_UNAVAILABLE_MSG = "OpenFoodFactsApi unavailable!";

		@Override
		public JsonNode getProduct(String barcode, @RequestHeader("User-Agent") String userAgentHeader) {
			throw new IllegalStateException(SERVICE_UNAVAILABLE_MSG);
		}
	}
}
