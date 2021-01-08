package com.purefour.mainservice.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCard {

    private String barcode;

    private String name;
    private String brand;
    private String photoUrl;
    private Category category;

    private Price price;

    private int totalQuantity;
    private MeasurementUnit measurementUnit;

    private Nutriments nutriments;
}
