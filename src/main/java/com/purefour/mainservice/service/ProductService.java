package com.purefour.mainservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.purefour.mainservice.config.ServiceInfo;
import com.purefour.mainservice.feign.DatabaseClient;
import com.purefour.mainservice.feign.OpenFoodFactsClient;
import com.purefour.mainservice.model.exceptions.BadRequestException;
import com.purefour.mainservice.model.exceptions.NotFoundException;
import com.purefour.mainservice.model.product.Container;
import com.purefour.mainservice.model.product.Product;
import com.purefour.mainservice.model.product.ProductCard;
import com.purefour.mainservice.model.product.SharedInfo;
import com.purefour.mainservice.service.mapper.FieldUtils;
import com.purefour.mainservice.service.mapper.ProductCardMapperService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {

	private final ServiceInfo serviceInfo;
	private final ProductCardMapperService productMapperService;
	private final OpenFoodFactsClient foodFactsClient;
	private final DatabaseClient databaseClient;
	private final AllegroService allegroService;

	public ProductCard searchProduct(String barcode) throws NotFoundException {
		Optional<ProductCard> productCardFromDatabase;

		try {
			productCardFromDatabase = Optional.ofNullable(databaseClient.getProductCard(barcode));
		} catch (NotFoundException e) {
			productCardFromDatabase = Optional.empty();
		}

		return productCardFromDatabase.isPresent() ?
                productCardFromDatabase.get() : searchProductCardFromFoodApi(barcode);
	}

	@Cacheable(value = "scanProducts", key = "#barcode")
	public ProductCard searchProductCardFromFoodApi(String barcode) throws NotFoundException {
		final JsonNode fullJsonProduct = foodFactsClient.getProduct(barcode, serviceInfo.getApiInfo());
		log.debug("FoodFactsClient product search response: {}", fullJsonProduct);

		checkApiResponseStatus(fullJsonProduct);

		final ProductCard productFromFoodApi = productMapperService.mapToTarget(fullJsonProduct);
		return enrichProduct(productFromFoodApi);
	}

	public Product addProduct(String userUuid, Product product) throws NotFoundException, BadRequestException {
		return databaseClient.add(userUuid, product);
	}

	public Product updateProduct(Product product) throws NotFoundException, BadRequestException {
		return databaseClient.update(product);
	}

	public Product getProduct(String productUuid) {
		return databaseClient.getProduct(productUuid);
	}

	public List<Product> getAllProducts(String userUuid) {
		return databaseClient.getAllProducts(userUuid);
	}

	public void deleteProduct(String productUuid, String userUuid) {
		databaseClient.deleteProduct(userUuid, productUuid);
	}

	public Container getContainer(String userUuid) throws NotFoundException {
		return databaseClient.getContainer(userUuid);
	}

	public SharedInfo getContainerSharedInfo(String userUuid) throws NotFoundException {
		return databaseClient.getContainerSharedInfo(userUuid);
	}

	public void shareContainer(String userUuid, String targetContainerUuid) throws NotFoundException {
		databaseClient.shareContainer(userUuid, targetContainerUuid);
	}

	private ProductCard enrichProduct(ProductCard productCard) {
		productCard.setPrice(allegroService.getProductPriceByBarcode(productCard.getBarcode()));
		return productCard;
	}

	private void checkApiResponseStatus(JsonNode jsonNode) throws NotFoundException {
		try {
			final JsonNode apiStatusNode = jsonNode.get(FieldUtils.STATUS);
			final int apiStatus = apiStatusNode.asInt();
			if (apiStatus == 1) {
				return;
			}
		} catch (Exception ignored) {
		}

		throw new NotFoundException("Searched product has not been found!");
	}
}
