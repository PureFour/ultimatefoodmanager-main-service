package com.purefour.mainservice.service.mapper.fields;

import com.purefour.mainservice.service.mapper.FieldTarget;

public class BarcodeField extends FieldTarget<String> {

	@Override
	public String getName() {
		return "barcode";
	}
}
