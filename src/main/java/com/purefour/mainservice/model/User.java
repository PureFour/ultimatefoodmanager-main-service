package com.purefour.mainservice.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private UUID uuid;
	private String email;
	private String login;
	private String password;
}
