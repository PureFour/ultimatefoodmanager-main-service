package com.purefour.mainservice.feign;

import com.fasterxml.jackson.databind.JsonNode;

public interface OpenFoodFactsClient {

	JsonNode getProduct(String barcode, String userAgentHeader);
}
