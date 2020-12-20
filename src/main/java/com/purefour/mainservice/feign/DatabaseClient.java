package com.purefour.mainservice.feign;

import com.purefour.mainservice.model.product.Product;
import com.purefour.mainservice.model.user.FindUserQuery;
import com.purefour.mainservice.model.user.RegisterRequest;
import com.purefour.mainservice.model.user.User;
import feign.Feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(
	name = "database-service",
	url = "${database.protocol}://${database.host}:${database.port}/_db/${database.dbName}/${database.mountPath}",
	fallback = DatabaseClient.DatabaseClientFallback.class,
	configuration = DatabaseClient.DatabaseClientConfiguration.class
)
public interface DatabaseClient {

	//USERS

	@GetMapping("users/{uuid}")
	User getUser(@PathVariable("uuid") String uuid);

	@PostMapping("users")
	User findUser(FindUserQuery findUserQuery);

	@PostMapping("users/signUp")
	User addUser(RegisterRequest registerRequest);

	@DeleteMapping("users/{uuid}")
	void deleteUser(@PathVariable("uuid") String uuid);

	//PRODUCTS

	@PostMapping("products/{userUuid}")
	Product add(@PathVariable("userUuid") String userUuid, Product product);

	@GetMapping("products/{uuid}")
	Product getProduct(@PathVariable String uuid);

	@GetMapping("products/{userUuid}/all")
	List<Product> getAllProducts(@PathVariable("userUuid") String userUuid);

	@DeleteMapping("products/{uuid}")
	void deleteProduct(@PathVariable String uuid);

	class DatabaseClientFallback implements DatabaseClient {
		private static final String SERVICE_UNAVAILABLE_MSG = "Database unavailable.";

		@Override
		public User getUser(String uuid) {
			throw new IllegalStateException(SERVICE_UNAVAILABLE_MSG);
		}

		@Override
		public User findUser(FindUserQuery findUserQuery) {
			throw new IllegalStateException(SERVICE_UNAVAILABLE_MSG);
		}

		@Override
		public User addUser(RegisterRequest registerRequest) {
			throw new IllegalStateException(SERVICE_UNAVAILABLE_MSG);
		}

		@Override
		public void deleteUser(String uuid) {
			throw new IllegalStateException(SERVICE_UNAVAILABLE_MSG);
		}

		@Override
		public Product add(String userUuid, Product product) {
			throw new IllegalStateException(SERVICE_UNAVAILABLE_MSG);
		}

		@Override
		public Product getProduct(String productUuid) {
			throw new IllegalStateException(SERVICE_UNAVAILABLE_MSG);
		}

		@Override
		public List<Product> getAllProducts(String userUuid) {
			throw new IllegalStateException(SERVICE_UNAVAILABLE_MSG);
		}

		@Override
		public void deleteProduct(String productUuid) {
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
