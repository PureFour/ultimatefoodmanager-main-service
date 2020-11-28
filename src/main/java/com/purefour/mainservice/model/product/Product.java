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
	private String barcode;
	private Category category;

	private float price;
	private String currency;

	private MeasurementUnit measurementUnit;
	private int quantity;
	private Nutriments nutriments;
	private LocalDate expiryDate;
}
