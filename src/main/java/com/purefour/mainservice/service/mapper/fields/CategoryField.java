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
		final String categoryName = extractFirstCategoryTag(categoriesObject);
		return Category.forName(categoryName);
	}

	private String extractFirstCategoryTag(Object categoryObject) {
		String categoryTag = ((ArrayNode) categoryObject).get(0).asText();

		if (categoryTag.isEmpty()) {
			return " ";
		}

		final String[] categoryName = categoryTag.split("(\\w+[:])");
		return categoryName[1];
	}
}
