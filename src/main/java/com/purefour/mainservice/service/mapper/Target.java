package com.purefour.mainservice.service.mapper;

import com.fasterxml.jackson.databind.JsonNode;

public interface Target<T> {

	String getName();

	String getStepKey();

	T getMappedValue(String nodeKey, JsonNode jsonNode);
}
