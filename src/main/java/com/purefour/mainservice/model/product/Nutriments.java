package com.purefour.mainservice.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Nutriments {

	private float energy;

	private float fat;
	private float saturatedFat;
	private float insatiableFat;

	private float proteins;

	private float carbohydrates;
	private float sugars;
	private float fiber;

	private float salt;

	private float sodium;
}
