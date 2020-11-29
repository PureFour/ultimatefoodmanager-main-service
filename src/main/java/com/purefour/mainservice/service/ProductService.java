package com.purefour.mainservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.purefour.mainservice.config.ServiceInfo;
import com.purefour.mainservice.feign.OpenFoodFactsClient;
import com.purefour.mainservice.model.exceptions.BadRequestException;
import com.purefour.mainservice.model.exceptions.NotFoundException;
import com.purefour.mainservice.model.product.Product;
import com.purefour.mainservice.service.mapper.ProductMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

	private final ServiceInfo serviceInfo;
	private final ProductMapperService productMapperService;
	private final OpenFoodFactsClient foodFactsClient;

	public Product searchProduct(String barcode) throws NotFoundException, BadRequestException {
		final JsonNode fullJsonProduct = foodFactsClient.getProduct(barcode, serviceInfo.getApiInfo());

		checkApiResponseStatus(fullJsonProduct);

		final Product productFromFoodApi = productMapperService.mapToTarget(fullJsonProduct);
		return productFromFoodApi; //TODO zrobic enrichment np. z ceną itd...
	}

	private void checkApiResponseStatus(JsonNode JsonNode) throws NotFoundException, BadRequestException {

	}
}
