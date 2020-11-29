package com.purefour.mainservice.service.mapper;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.GenericTypeResolver;

@AllArgsConstructor
public abstract class Mapper<M> {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	protected abstract Map<String, Target> getMapping();

	public <T> T convertToPojo(Object object, Class<T> targetType) {
		return objectMapper.convertValue(object, targetType);
	}

	protected <M> M mapToObject(Map<String, Object> map) {
		return convertToPojo(map, (Class<M>) GenericTypeResolver.resolveTypeArgument(getClass(), Mapper.class));
	}

	public M mapToTarget(JsonNode jsonNode) {
		final Map<String, Object> map = new HashMap<>();

		getMapping().forEach(
			(key, target) -> map.put(target.getName(), target.getMappedValue(key, jsonNode)));

		return mapToObject(map);
	}

	public <T> T findNodeVariable(JsonNode jsonNode, String variablePath) {
		return (T) jsonNode.findValue(variablePath);
	}
}
