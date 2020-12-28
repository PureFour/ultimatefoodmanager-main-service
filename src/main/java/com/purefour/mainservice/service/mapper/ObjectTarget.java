package com.purefour.mainservice.service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public abstract class ObjectTarget<T> extends Mapper<T> implements Target<T> {

	protected abstract Map<String, Target> getMapping();

	@Override
	public String getStepKey() {
		return "";
	}

	@Override
	public T getMappedValue(String nodeKey, JsonNode jsonNode) {
		return mapToTarget(jsonNode);
	}
}
