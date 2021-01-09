package com.purefour.mainservice.endpoints;

import com.purefour.mainservice.model.exceptions.BadRequestException;
import com.purefour.mainservice.model.exceptions.NotFoundException;
import com.purefour.mainservice.model.product.Container;
import com.purefour.mainservice.model.product.SharedInfo;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.UUID;

@RestController
@Api(tags = "Container Controller")
@RequestMapping(value = "/containers", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ContainerController {

	private final ProductService productService;

	@ApiOperation(value = "Get user container uuid")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Operation successful!"),
			@ApiResponse(code = 404, message = "Container not found!", response = NotFoundException.class)
	})
	@GetMapping("uuid")
	public ResponseEntity<UUID> getContainerUuid(
			@ApiIgnore @RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String authorizationToken) throws NotFoundException {
		final String userUuid = JwtUtil.extractUserUuid(authorizationToken);
		final Container userContainer = productService.getContainer(userUuid);
		return ResponseEntity.ok(userContainer.getUuid());
	}

	@ApiOperation(value = "Share user container")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Operation successful!"),
			@ApiResponse(code = 404, message = "Container not found!", response = NotFoundException.class),
			@ApiResponse(code = 400, message = "Bad request!", response = BadRequestException.class),
	})
	@PutMapping("share")
	public ResponseEntity<String> shareContainer(
			@RequestParam String targetContainerUuid,
			@ApiIgnore @RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String authorizationToken) throws NotFoundException {
		final String userUuid = JwtUtil.extractUserUuid(authorizationToken);
		productService.shareContainer(userUuid, targetContainerUuid);
		return ResponseEntity.ok(userUuid);
	}

	@ApiOperation(value = "Get user container shared info")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Operation successful!"),
			@ApiResponse(code = 404, message = "Container not found!", response = NotFoundException.class)
	})
	@GetMapping("sharedInfo")
	public ResponseEntity<SharedInfo> getContainerSharedInfo(
			@ApiIgnore @RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String authorizationToken) throws NotFoundException {
		final String userUuid = JwtUtil.extractUserUuid(authorizationToken);
		return ResponseEntity.ok(productService.getContainerSharedInfo(userUuid));
	}
}
