package com.purefour.mainservice.service;

import com.purefour.mainservice.model.exceptions.UnauthorizedException;
import com.purefour.mainservice.model.user.AuthenticationResponse;
import com.purefour.mainservice.model.user.FindUserQuery;
import com.purefour.mainservice.model.user.LoginRequest;
import com.purefour.mainservice.model.user.User;
import com.purefour.mainservice.security.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationService {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public AuthenticationResponse authenticateUser(LoginRequest loginRequest) throws UnauthorizedException {
		final User user = userService.findUser(
				FindUserQuery.builder()
						.email(loginRequest.getEmail())
						.build());
		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new UnauthorizedException("Wrong password!");
		}
		return AuthenticationResponse.builder().token(JwtUtil.generateToken(user)).build();
	}
}
