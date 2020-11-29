package com.purefour.mainservice.service.mapper;

import java.util.HashMap;
import java.util.Map;

import com.purefour.mainservice.model.product.Product;
import com.purefour.mainservice.service.mapper.fields.BarcodeField;
import com.purefour.mainservice.service.mapper.fields.BrandField;
import com.purefour.mainservice.service.mapper.fields.CategoryField;
import com.purefour.mainservice.service.mapper.fields.MeasurementUnitField;
import com.purefour.mainservice.service.mapper.fields.NutrimentsField;
import com.purefour.mainservice.service.mapper.fields.ProductNameField;
import com.purefour.mainservice.service.mapper.fields.QuantityField;
import org.springframework.stereotype.Service;

@Service
public class ProductMapperService extends Mapper<Product> {

	private static final String NAME_TARGET_PATH = "product_name";
	private static final String BRAND_TARGET_PATH = "brands";
	private static final String BARCODE_TARGET_PATH = "code";
	private static final String CATEGORY_TARGET_PATH = "categories_tags";
	private static final String MEASUREMENT_UNIT = "measurementUnit";
	private static final String QUANTITY_TARGET_PATH = "quantity";
	private static final String NUTRIMENTS_TARGET_PATH = "nutriments";

	private static final Map<String, Target> MAPPING = new HashMap<>();

	static {
		MAPPING.put(NAME_TARGET_PATH, new ProductNameField());
		MAPPING.put(BRAND_TARGET_PATH, new BrandField());
		MAPPING.put(BARCODE_TARGET_PATH, new BarcodeField());
		MAPPING.put(CATEGORY_TARGET_PATH, new CategoryField());
		MAPPING.put(MEASUREMENT_UNIT, new MeasurementUnitField());
		MAPPING.put(QUANTITY_TARGET_PATH, new QuantityField());
		MAPPING.put(NUTRIMENTS_TARGET_PATH, new NutrimentsField());
	}

	@Override
	protected Map<String, Target> getMapping() {
		return MAPPING;
	}
}
