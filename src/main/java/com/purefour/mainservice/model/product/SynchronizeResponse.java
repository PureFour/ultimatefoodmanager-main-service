package com.purefour.mainservice.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SynchronizeResponse {

    private List<Product> synchronizedProducts;
    private String status;
}
