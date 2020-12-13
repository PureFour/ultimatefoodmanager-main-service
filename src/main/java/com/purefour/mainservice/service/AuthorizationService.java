package com.purefour.mainservice.service;

import com.purefour.mainservice.model.exceptions.UnauthorizedException;
import com.purefour.mainservice.model.user.AuthenticationResponse;
import com.purefour.mainservice.model.user.FindUserQuery;
import com.purefour.mainservice.model.user.LoginRequest;
import com.purefour.mainservice.model.user.User;
import com.purefour.mainservice.security.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationService {

	private final UserService userService;

	//TODO moze przeniesc to do bazy?
	public AuthenticationResponse authenticateUser(LoginRequest loginRequest) throws UnauthorizedException {
		final User user = userService.findUser(
				FindUserQuery.builder()
						.email(loginRequest.getEmail())
						.build());
		if (!loginRequest.getPassword().equals(user.getPassword())) {
			throw new UnauthorizedException("Wrong password!");
		}
		return AuthenticationResponse.builder().token(JwtUtil.generateToken(user)).build();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
