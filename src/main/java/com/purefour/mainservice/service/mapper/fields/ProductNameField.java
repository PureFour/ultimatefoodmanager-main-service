package com.purefour.mainservice.service.mapper.fields;

import com.purefour.mainservice.service.mapper.FieldTarget;

public class ProductNameField extends FieldTarget<String> {

	@Override
	public String getName() {
		return "name";
	}
}
