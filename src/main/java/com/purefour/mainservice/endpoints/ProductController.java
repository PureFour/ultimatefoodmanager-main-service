package com.purefour.mainservice.endpoints;

import com.purefour.mainservice.model.exceptions.BadRequestException;
import com.purefour.mainservice.model.exceptions.NotFoundException;
import com.purefour.mainservice.model.product.Product;
import com.purefour.mainservice.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Product Controller")
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ProductController {

	private final ProductService productService;

	@ApiOperation(value = "Search product by its barcode")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Operation successful!"),
		@ApiResponse(code = 400, message = "Bad request!"),
		@ApiResponse(code = 404, message = "Product not found!", response = NotFoundException.class),
	})
	@GetMapping("search")
	public ResponseEntity<Product> searchProduct(@RequestParam String barcode) throws NotFoundException, BadRequestException {
		return ResponseEntity.ok(productService.searchProduct(barcode));
	}

}
