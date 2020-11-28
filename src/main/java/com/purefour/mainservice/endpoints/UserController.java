package com.purefour.mainservice.endpoints;

import com.purefour.mainservice.model.AuthenticationResponse;
import com.purefour.mainservice.model.LoginRequest;
import com.purefour.mainservice.model.RegisterRequest;
import com.purefour.mainservice.model.RegisterResponse;
import com.purefour.mainservice.model.User;
import com.purefour.mainservice.model.exceptions.ConflictException;
import com.purefour.mainservice.model.exceptions.NotFoundException;
import com.purefour.mainservice.service.AuthorizationService;
import com.purefour.mainservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "User Controller")
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {

	private final UserService userService;
	private final AuthorizationService authorizationService;

	@ApiOperation(value = "Authenticate user")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Operation successful!"),
		@ApiResponse(code = 401, message = "Unauthorized!"),
		@ApiResponse(code = 404, message = "User not found!", response = NotFoundException.class),
	})
	@PostMapping("signIn")
	public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authorizationService.authenticateUser(loginRequest));
	}

    @ApiOperation(value = "Add user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User added!"),
            @ApiResponse(code = 409, message = "User exist!", response = ConflictException.class)
    })
    @PostMapping("signUp")
    public ResponseEntity<RegisterResponse> addUser(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.addUser(registerRequest));
    }

    @ApiOperation(value = "Get user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Got user!"),
            @ApiResponse(code = 404, message = "User not found!", response = NotFoundException.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token (starts with 'Bearer')", dataType = "string", paramType = "header", required = false) })
    @GetMapping()
    public ResponseEntity<User> getUserByUsername(@RequestParam String uuid) {
        return ResponseEntity.ok(userService.getUser(uuid));
    }

    @ApiOperation(value = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation successful!"),
            @ApiResponse(code = 404, message = "User not found!", response = NotFoundException.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token (starts with 'Bearer')", dataType = "string", paramType = "header", required = false) })
    @DeleteMapping()
    public ResponseEntity<String> deleteUser(@RequestParam String uuid) {
        userService.deleteUser(uuid);
	    return ResponseEntity.ok(uuid);
	}
}
