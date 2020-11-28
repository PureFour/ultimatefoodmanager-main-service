package com.purefour.mainservice.service.mapper;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.purefour.mainservice.model.product.Product;
import com.purefour.mainservice.service.mapper.fields.ProductNameField;

public class ProductMapperService {

	private static final String NAME = "NAME";
	private static final String BRAND = "BRAND";
	private static final String BARCODE = "BARCODE";
	private static final String CATEGORY = "CATEGORY";
	private static final String PRICE = "NAME";
	private static final String CURRENCY = "NAME";
	private static final String MEASUREMENT_UNIT = "NAME";
	private static final String QUANTITY = "NAME";
	private static final String NUTRIMENTS = "NAME";
	private static final String EXPIRY_DATE = "NAME";


	private static final Map<String, Target> MAPPING = new HashMap<>();

	static {
		MAPPING.put("generic_name_pl", new ProductNameField());
	}

	public Product mapToProduct(JsonNode productNode) {
		return new Product();
	}
}
