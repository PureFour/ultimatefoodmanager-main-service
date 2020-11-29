package com.purefour.mainservice.service.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

	private ObjectNode mapToTargetUtil(Map<String, Target> map, JsonNode jsonNode) {
		ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
		map.forEach((targetKey, fieldTarget) -> {
			Object variableValue = fieldTarget.getMappedValue(targetKey, jsonNode);
			ObjectNode leaf = (ObjectNode) this.getLeafWithValue(objectNode, targetKey);
			JsonNode objectValue = objectMapper.convertValue(variableValue, JsonNode.class);
			leaf.set(fieldTarget.getName(), objectValue);
		});
		return objectNode;
	}

	public JsonNode getLeafWithValue(ObjectNode json, String key) {
		JsonNode leaf = json;
		for (String pathElement : pathAsArray(key)) {
			final JsonNode child = leaf.get(pathElement);
			if (child != null && child.isNull()) {
				leaf = JsonNodeFactory.instance.objectNode();
			} else {
				leaf = leaf.with(pathElement);
			}
		}
		return leaf;
	}

	private List<String> pathAsArray(String path) {
		if (path != null) {
			return Arrays.asList(path.split("\\."));
		}
		return new ArrayList<>();
	}
}
