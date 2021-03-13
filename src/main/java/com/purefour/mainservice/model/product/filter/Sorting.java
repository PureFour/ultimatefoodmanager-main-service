package com.purefour.mainservice.model.product.filter;

import lombok.Data;

@Data
public class Sorting {
    private Selector selector;
    private boolean ascending;
}
