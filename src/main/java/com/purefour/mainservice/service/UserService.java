package com.purefour.mainservice.service;

import com.purefour.mainservice.feign.DatabaseClient;
import com.purefour.mainservice.model.RegisterRequest;
import com.purefour.mainservice.model.User;
import com.purefour.mainservice.model.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final DatabaseClient databaseClient;

	public void addUser(RegisterRequest registerRequest) {
		databaseClient.addUser(registerRequest);
	}

    public User getUser(String uuid) throws NotFoundException {
        return databaseClient.getUser(uuid);
    }

	public void deleteUser(String uuid) {
		databaseClient.deleteUser(uuid);
	}
}
