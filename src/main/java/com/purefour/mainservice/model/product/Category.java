package com.purefour.mainservice.model.product;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public enum Category {
	NOT_FOUND(" "),
	PLANT_BASED_FOODS("plant-based-foods-and-beverages"),
	DAIRIES("dairies"),
	GROCERIES("groceries"),
	BEVERAGES("beverages");

	@Getter
	private String name;

	public static Category forName(String name) {
		return Arrays.stream(Category.values())
			.filter(category -> category.getName().equals(name)).findFirst().orElseGet(() -> {
				log.error("Didn't find matching category for: {}", name);
				return Category.NOT_FOUND;
			});
	}
}
