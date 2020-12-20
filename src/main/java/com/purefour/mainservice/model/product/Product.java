package com.purefour.mainservice.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	private UUID uuid;

	private String name;
	private String brand;
	private String photoUrl;
	private String barcode;
	private Category category;

	private Price price;

	private int totalQuantity;
	private int quantity;
	private MeasurementUnit measurementUnit;

	private Nutriments nutriments;
	private Metadata metadata;
}
