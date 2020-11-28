package com.purefour.mainservice.service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Target<T> {

	private T targetField;

	protected abstract T mapToObject(JsonNode jsonNode);
}
