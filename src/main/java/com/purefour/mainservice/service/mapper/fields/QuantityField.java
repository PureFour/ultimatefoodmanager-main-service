package com.purefour.mainservice.service.mapper.fields;

import com.fasterxml.jackson.databind.JsonNode;
import com.purefour.mainservice.service.mapper.FieldTarget;
import com.purefour.mainservice.service.mapper.FieldUtils;

public class QuantityField extends FieldTarget<Integer> {

	@Override
	public String getName() {
		return "quantity";
	}

	@Override
	public Integer getMappedValue(String nodeKey, JsonNode jsonNode) {
		final Object quantity = super.getMappedValue(nodeKey, jsonNode);
		return FieldUtils.getFirstNumber(quantity);
	}
}
