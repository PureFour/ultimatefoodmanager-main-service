package com.purefour.mainservice.model.product;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Metadata {

	private boolean isSynchronized;
	private boolean toBeDeleted;
	private LocalDate createdDate;
	private LocalDate expiryDate;
}
