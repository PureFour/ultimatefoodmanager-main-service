package com.purefour.mainservice.feign;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@ConditionalOnProperty(
	name = "openFoodFactsApi",
	havingValue = "fake")
@Service
public class OpenFoodFactsFakeClient implements OpenFoodFactsClient {

	private static final String FAKE_PRODUCT_BARCODE = "8008698005347";
	private static final String FAKE_FOUND_PRODUCT_RESPONSE_PATH = "/fakeApi/sampleFoundProductResponse.json";
	private static final String FAKE_NOT_FOUND_PRODUCT_RESPONSE_PATH = "/fakeApi/sampleNotFoundProductResponse.json";

	public JsonNode getProduct(String barcode, String userAgentHeader) {
		if (barcode.equals(FAKE_PRODUCT_BARCODE)) {
			return loadJsonFromResource(FAKE_FOUND_PRODUCT_RESPONSE_PATH);
		}
		return loadJsonFromResource(FAKE_NOT_FOUND_PRODUCT_RESPONSE_PATH);
	}

	private JsonNode loadJsonFromResource(String jsonPath) {
		try (InputStream inputStream = this.getClass().getResourceAsStream(jsonPath)) {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(inputStream, JsonNode.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
