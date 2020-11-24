package com.purefour.mainservice.service;

import com.purefour.mainservice.model.AuthenticationResponse;
import com.purefour.mainservice.model.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

	public AuthenticationResponse authenticateUser(LoginRequest loginRequest) {
		return AuthenticationResponse.builder().jwtToken("OK").build();
	}
}
