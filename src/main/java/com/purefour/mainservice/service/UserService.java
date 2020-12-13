package com.purefour.mainservice.service;

import com.purefour.mainservice.feign.DatabaseClient;
import com.purefour.mainservice.model.user.FindUserQuery;
import com.purefour.mainservice.model.user.RegisterRequest;
import com.purefour.mainservice.model.user.RegisterResponse;
import com.purefour.mainservice.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.purefour.mainservice.security.util.JwtUtil.generateToken;

@Service
@AllArgsConstructor
public class UserService {

    private final DatabaseClient databaseClient;

	public RegisterResponse addUser(RegisterRequest registerRequest) {
		final User user = databaseClient.addUser(registerRequest);
		return RegisterResponse.builder()
			.token(generateToken(user))
			.user(user)
			.build();
	}

    public User getUser(String uuid) {
        return databaseClient.getUser(uuid);
    }

	public User findUser(FindUserQuery findUserQuery) {
		return databaseClient.findUser(findUserQuery);
	}

	public void deleteUser(String uuid) {
		databaseClient.deleteUser(uuid);
	}
}
