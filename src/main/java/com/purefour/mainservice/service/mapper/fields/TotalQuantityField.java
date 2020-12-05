package com.purefour.mainservice.service.mapper.fields;

import com.fasterxml.jackson.databind.JsonNode;
import com.purefour.mainservice.service.mapper.FieldTarget;
import com.purefour.mainservice.service.mapper.FieldUtils;

public class TotalQuantityField extends FieldTarget<Integer> {

	@Override
	public String getName() {
		return "totalQuantity";
	}

	@Override
	public Integer getMappedValue(String nodeKey, JsonNode jsonNode) {
		final Object quantity = super.getMappedValue(nodeKey, jsonNode);
		return FieldUtils.getFirstNumber(quantity);
	}
}
