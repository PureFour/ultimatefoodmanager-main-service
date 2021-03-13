package com.purefour.mainservice.model.product.filter;

import lombok.Data;

@Data
public class Filter<T> {

    private Selector selector;
    private Range<T> range;
}
