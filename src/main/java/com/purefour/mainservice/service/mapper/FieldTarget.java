package com.purefour.mainservice.service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class FieldTarget<T> implements Target<T> {

	@Override
	public T getMappedValue(String nodeKey, JsonNode jsonNode) {
		return (T) getVariableValue(jsonNode.findValue(nodeKey));
	}

	private Object getVariableValue(JsonNode variableValue) {
		if (variableValue != null) {
			return variableValue.isTextual() ? variableValue.asText() : variableValue;
		}
		return null;
	}
}
