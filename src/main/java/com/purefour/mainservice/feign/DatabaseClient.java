package com.purefour.mainservice.feign;

import com.purefour.mainservice.model.RegisterRequest;
import com.purefour.mainservice.model.User;
import feign.Feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
	name = "database-service",
	url = "${database.protocol}://${database.host}:${database.port}/_db/${database.dbName}/${database.mountPath}",
	fallback = DatabaseClient.DatabaseClientFallback.class,
	configuration = DatabaseClient.DatabaseClientConfiguration.class
)
public interface DatabaseClient {

	@GetMapping("users/{uuid}")
	User getUser(@PathVariable("uuid") String uuid);

	@PostMapping("users")
	void addUser(RegisterRequest registerRequest);

	@DeleteMapping("users/{uuid}")
	void deleteUser(@PathVariable("uuid") String uuid);

	class DatabaseClientFallback implements DatabaseClient {
		private static final String SERVICE_UNAVAILABLE_MSG = "Database unavailable.";

		@Override
		public User getUser(String uuid) {
			throw new IllegalStateException(SERVICE_UNAVAILABLE_MSG);
		}

		@Override
		public void addUser(RegisterRequest registerRequest) {
			throw new IllegalStateException(SERVICE_UNAVAILABLE_MSG);
		}

		@Override
		public void deleteUser(String uuid) {
			throw new IllegalStateException(SERVICE_UNAVAILABLE_MSG);
		}
	}

	class DatabaseClientConfiguration {
		@Bean
		public Feign.Builder feignBuilder() {
			return Feign.builder()
				.errorDecoder(new DatabaseClientErrorDecoder());
		}
	}
}
