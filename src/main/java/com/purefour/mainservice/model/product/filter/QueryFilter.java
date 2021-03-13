package com.purefour.mainservice.model.product.filter;

import lombok.Data;

import java.util.List;

@Data
public class QueryFilter {

   private List<Filter> filters;
   private Sorting sorting;
}
