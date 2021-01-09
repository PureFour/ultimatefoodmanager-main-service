package com.purefour.mainservice.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Metadata {

	private boolean isSynchronized;
	private boolean toBeDeleted;
	private boolean shared;
	private LocalDate createdDate;
	private LocalDate expiryDate;
}
