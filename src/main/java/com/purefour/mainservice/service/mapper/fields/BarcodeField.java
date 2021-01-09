package com.purefour.mainservice.service.mapper.fields;

import com.purefour.mainservice.service.mapper.FieldTarget;

public class BarcodeField extends FieldTarget<String> {

	private static final String PRODUCT = "product";

	@Override
	public String getName() {
		return "barcode";
	}

	@Override
	public String getStepKey() {
		return PRODUCT;
	}
}
