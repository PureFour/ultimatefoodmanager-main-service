package com.purefour.mainservice.endpoints;

import com.purefour.mainservice.model.exceptions.BadRequestException;
import com.purefour.mainservice.model.exceptions.ConflictException;
import com.purefour.mainservice.model.exceptions.NotFoundException;
import com.purefour.mainservice.model.product.Product;
import com.purefour.mainservice.model.product.ProductCard;
import com.purefour.mainservice.security.util.JwtUtil;
import com.purefour.mainservice.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@Api(tags = "Product Controller")
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ProductController {

	private final ProductService productService;

	@ApiOperation(value = "Search product by its barcode")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Operation successful!"),
		@ApiResponse(code = 404, message = "Product not found!", response = NotFoundException.class)
	})
	@GetMapping("search")
	public ResponseEntity<ProductCard> searchProduct(@RequestParam String barcode) throws NotFoundException {
		return ResponseEntity.ok(productService.searchProduct(barcode));
	}

	@ApiOperation(value = "Add product")
	@ApiResponses(value = {
		@ApiResponse(code = 201, message = "Operation successful!"),
		@ApiResponse(code = 400, message = "Bad request!"),
		@ApiResponse(code = 409, message = "Product already exist!", response = ConflictException.class),
	})
	@PostMapping
	public ResponseEntity<Product> addProduct(
		@RequestBody Product product,
		@ApiIgnore @RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String authorizationToken) throws NotFoundException, BadRequestException {
		final String userUuid = JwtUtil.extractUserUuid(authorizationToken);
		return ResponseEntity.ok(productService.addProduct(userUuid, product));
	}

	@ApiOperation(value = "Update product")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Operation successful!"),
		@ApiResponse(code = 400, message = "Bad request!"),
		@ApiResponse(code = 404, message = "Product not found!", response = NotFoundException.class)
	})
	@PutMapping
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws NotFoundException, BadRequestException {
		return ResponseEntity.ok(productService.updateProduct(product));
	}

	@ApiOperation(value = "Get product")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Operation successful!"),
		@ApiResponse(code = 404, message = "Product not found!")
	})
	@GetMapping
	public ResponseEntity<Product> getProduct(@RequestParam String uuid) throws NotFoundException {
		return ResponseEntity.ok(productService.getProduct(uuid));
	}

	@ApiOperation(value = "Get all products")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Operation successful!"),
		@ApiResponse(code = 404, message = "Product not found!")
	})
	@GetMapping("all")
	public ResponseEntity<List<Product>> getAllProducts(
		@ApiIgnore @RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String authorizationToken) throws NotFoundException {
		final String userUuid = JwtUtil.extractUserUuid(authorizationToken);
		return ResponseEntity.ok(productService.getAllProducts(userUuid));
	}

	@ApiOperation(value = "Synchronize all products")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Operation successful!"),
			@ApiResponse(code = 400, message = "Bad request!")
	})
	@PutMapping("synchronizeAll")
	public ResponseEntity<List<Product>> synchronizeAllProducts(@RequestBody List<Product> products,
		@ApiIgnore @RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String authorizationToken) throws NotFoundException, BadRequestException {
		final String userUuid = JwtUtil.extractUserUuid(authorizationToken);
		return ResponseEntity.ok(productService.synchronizeAllProducts(products, userUuid));
	}

	@ApiOperation(value = "Delete product")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Operation successful!"),
		@ApiResponse(code = 404, message = "Product not found!")
	})
	@DeleteMapping
	public ResponseEntity<String> deleteProduct(@RequestParam String uuid,
		@ApiIgnore @RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String authorizationToken) throws NotFoundException {
		final String userUuid = JwtUtil.extractUserUuid(authorizationToken);
		productService.deleteProduct(uuid, userUuid);
		return ResponseEntity.ok(uuid);
	}
}
