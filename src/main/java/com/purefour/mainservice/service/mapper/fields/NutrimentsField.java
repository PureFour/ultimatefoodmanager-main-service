package com.purefour.mainservice.service.mapper.fields;

import com.purefour.mainservice.model.product.Nutriments;
import com.purefour.mainservice.service.mapper.FieldTarget;
import com.purefour.mainservice.service.mapper.ObjectTarget;
import com.purefour.mainservice.service.mapper.Target;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

public class NutrimentsField extends ObjectTarget<Nutriments> {

	private static final String ENERGY_TARGET_PATH = "energy-kcal_value";
	private static final String FAT_TARGET_PATH = "fat_value";
	private static final String SATURATED_FAT_TARGET_PATH = "saturated-fat_value";
	private static final String INSATIABLE_FAT_TARGET_PATH = "insatiable-fat_value";
	private static final String CARBOHYDRATES_TARGET_PATH = "carbohydrates_value";
	private static final String SUGARS_TARGET_PATH = "sugars_value";
	private static final String FIBER_TARGET_PATH = "fiber_value";
	private static final String SALT_TARGET_PATH = "salt_value";
	private static final String SODIUM_TARGET_PATH = "sodium_value";

	private static final Map<String, Target> MAPPING = new HashMap<>();

	static {
		MAPPING.put(ENERGY_TARGET_PATH, new NutrimentField("energy"));
		MAPPING.put(FAT_TARGET_PATH, new NutrimentField("fat"));
		MAPPING.put(SATURATED_FAT_TARGET_PATH, new NutrimentField("saturatedFat"));
		MAPPING.put(INSATIABLE_FAT_TARGET_PATH, new NutrimentField("insatiableFat"));
		MAPPING.put(CARBOHYDRATES_TARGET_PATH, new NutrimentField("carbohydrates"));
		MAPPING.put(SUGARS_TARGET_PATH, new NutrimentField("sugars"));
		MAPPING.put(FIBER_TARGET_PATH, new NutrimentField("fiber"));
		MAPPING.put(SALT_TARGET_PATH, new NutrimentField("salt"));
		MAPPING.put(SODIUM_TARGET_PATH, new NutrimentField("sodium"));
	}

	@Override
	public String getName() {
		return "nutriments";
	}

	@Override
	protected Map<String, Target> getMapping() {
		return MAPPING;
	}

	@AllArgsConstructor
	static class NutrimentField extends FieldTarget<Float> {

		private final String name;

		@Override
		public String getName() {
			return this.name;
		}
	}
}
