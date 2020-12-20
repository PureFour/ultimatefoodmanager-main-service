package com.purefour.mainservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.purefour.mainservice.config.ServiceInfo;
import com.purefour.mainservice.feign.DatabaseClient;
import com.purefour.mainservice.feign.OpenFoodFactsClient;
import com.purefour.mainservice.model.exceptions.BadRequestException;
import com.purefour.mainservice.model.exceptions.NotFoundException;
import com.purefour.mainservice.model.product.Product;
import com.purefour.mainservice.service.mapper.FieldUtils;
import com.purefour.mainservice.service.mapper.ProductMapperService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {

	private final ServiceInfo serviceInfo;
	private final ProductMapperService productMapperService;
	private final OpenFoodFactsClient foodFactsClient;
	private final DatabaseClient databaseClient;
	private final AllegroService allegroService;

	@Cacheable(value = "scanProducts", key = "#barcode")
	public Product searchProduct(String barcode) throws NotFoundException {
		final JsonNode fullJsonProduct = foodFactsClient.getProduct(barcode, serviceInfo.getApiInfo());

		checkApiResponseStatus(fullJsonProduct);

		final Product productFromFoodApi = productMapperService.mapToTarget(fullJsonProduct);
		return enrichProduct(productFromFoodApi);
	}

	public Product addProduct(String userUuid, Product product) throws NotFoundException, BadRequestException {
		return databaseClient.add(userUuid, product);
	}

	private Product enrichProduct(Product product) {
		product.setQuantity(product.getTotalQuantity());
		product.setPrice(allegroService.getProductPriceByBarcode(product.getBarcode()));
		return product;
	}

	private void checkApiResponseStatus(JsonNode jsonNode) throws NotFoundException {
		try {
			final JsonNode apiStatusNode = productMapperService.findNodeVariable(jsonNode, FieldUtils.STATUS);
			final int apiStatus = apiStatusNode.asInt();
			if (apiStatus == 1) {
				return;
			}
		} catch (Exception ignored) {
		}

		throw new NotFoundException("Searched product has not been found!");
	}

	public Product getProduct(String productUuid) {
		return databaseClient.getProduct(productUuid);
	}

	public List<Product> getAllProducts(String userUuid) {
		return databaseClient.getAllProducts(userUuid);
	}

	public void deleteProduct(String productUuid) {
		databaseClient.deleteProduct(productUuid);
	}
}
