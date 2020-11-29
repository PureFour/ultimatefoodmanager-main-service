package com.purefour.mainservice.service.mapper.fields;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.purefour.mainservice.model.product.Category;
import com.purefour.mainservice.service.mapper.FieldTarget;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CategoryField extends FieldTarget<Category> {

	@Override
	public String getName() {
		return "category";
	}

	@Override
	public Category getMappedValue(String nodeKey, JsonNode jsonNode) {
		final Object categoriesObject = super.getMappedValue(nodeKey, jsonNode);
		return extractFirstCategoryTag(categoriesObject);
	}

	private Category extractFirstCategoryTag(Object categoryObject) {
		try {
			final String categoryTag = ((ArrayNode) categoryObject).get(0).asText();
			final String categoryName = categoryTag.split("(\\w+[:])")[1];
			return Category.forName(categoryName);
		} catch (Exception e) {
			return Category.NOT_FOUND;
		}
	}
}
