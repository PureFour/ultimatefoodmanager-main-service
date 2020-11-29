package com.purefour.mainservice.model.product;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@JsonIgnore
	private UUID uuid;

	private String name;
	private String brand;
	private String barcode;
	private Category category;

	private float price;
	private String currency;

	private MeasurementUnit measurementUnit; // g, ml, kg...
	private int quantity; // np. ilosc ml w puszcze coli
	private Nutriments nutriments;
	private LocalDate expiryDate;
}
