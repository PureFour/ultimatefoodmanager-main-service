package com.purefour.mainservice.service.mapper;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class ObjectTarget<T> extends Mapper<T> implements Target<T> {

	protected abstract Map<String, Target> getMapping();

	@Override
	public T getMappedValue(String nodeKey, JsonNode jsonNode) {
		return mapToTarget(jsonNode);
	}
}
