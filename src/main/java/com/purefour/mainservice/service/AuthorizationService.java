package com.purefour.mainservice.service;

import com.purefour.mainservice.model.user.AuthenticationResponse;
import com.purefour.mainservice.model.user.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

	public AuthenticationResponse authenticateUser(LoginRequest loginRequest) {
		return AuthenticationResponse.builder().token("OK").build();
	}
}
