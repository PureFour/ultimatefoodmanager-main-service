package com.purefour.mainservice.service.mapper.fields;

import com.fasterxml.jackson.databind.JsonNode;
import com.purefour.mainservice.model.product.MeasurementUnit;
import com.purefour.mainservice.service.mapper.FieldTarget;
import com.purefour.mainservice.service.mapper.FieldUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MeasurementUnitField extends FieldTarget<MeasurementUnit> {

	private static final String MEASUREMENT_UNIT_KEY = "quantity";

	@Override
	public String getName() {
		return "measurementUnit";
	}

	@Override
	public MeasurementUnit getMappedValue(String nodeKey, JsonNode jsonNode) {
		final Object quantityField = super.getMappedValue(MEASUREMENT_UNIT_KEY, jsonNode);
		return extractMeasurementUnit(quantityField);
	}

	private MeasurementUnit extractMeasurementUnit(Object quantityField) {
		String measurementUnitName = "";
		try {
			measurementUnitName = FieldUtils.getFirstWord(quantityField);
			return MeasurementUnit.valueOf(measurementUnitName.toUpperCase());
		} catch (Exception e) {
			log.error("Didn't find matching measurementUnit for: {}", measurementUnitName);
			return MeasurementUnit.NOT_FOUND;
		}
	}
}
