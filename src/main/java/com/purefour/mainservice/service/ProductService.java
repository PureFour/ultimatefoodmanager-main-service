package com.purefour.mainservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.purefour.mainservice.config.ServiceInfo;
import com.purefour.mainservice.feign.OpenFoodFactsClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

	private final ServiceInfo serviceInfo;
	private final OpenFoodFactsClient foodFactsClient;

	public JsonNode searchProduct(String barcode) {
		return foodFactsClient.getProduct(barcode, serviceInfo.getApiInfo());
	}

}
