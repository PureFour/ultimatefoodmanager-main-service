package com.purefour.mainservice.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private UUID uuid;
	private String email;
	private String login;
	private String password;
	private String notificationToken;
}
