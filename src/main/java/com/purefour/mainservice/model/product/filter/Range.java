package com.purefour.mainservice.model.product.filter;

import lombok.Data;

@Data
public class Range<T> {

    private T minimumValue;
    private T exactValue;
    private T maximumValue;
}
