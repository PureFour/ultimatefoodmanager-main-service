package com.purefour.mainservice.service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class FieldTarget<T> implements Target<T> {

	@Override
	public String getStepKey() {
		return "";
	}

	@Override
	public T getMappedValue(String nodeKey, JsonNode jsonNode) {
		final String stepKey = getStepKey();
		if (!stepKey.isEmpty()) {
			jsonNode = jsonNode.findValue(stepKey);
		}
		return (T) getVariableValue(jsonNode.findValue(nodeKey));
	}

	private Object getVariableValue(JsonNode variableValue) {
		if (variableValue != null) {
			return variableValue.isTextual() ? variableValue.asText() : variableValue;
		}
		return null;
	}
}
