package com.purefour.mainservice.model.product;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	private LocalDate expiryDate;
}
