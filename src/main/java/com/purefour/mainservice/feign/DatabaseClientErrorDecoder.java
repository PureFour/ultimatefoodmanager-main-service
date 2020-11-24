package com.purefour.mainservice.feign;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.purefour.mainservice.model.exceptions.BadRequestException;
import com.purefour.mainservice.model.exceptions.NotFoundException;
import com.purefour.mainservice.model.exceptions.UnhandledException;
import com.purefour.mainservice.service.util.CustomJacksonDecoder;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class DatabaseClientErrorDecoder implements ErrorDecoder {

	private static final CustomJacksonDecoder CUSTOM_JACKSON_DECODER = new CustomJacksonDecoder();

	@Override
	public Exception decode(String methodKey, Response response) {

		String responseBody = String.format("%s: %s", methodKey, getResponseBodyAsString(response));

		if (response.status() == HttpStatus.BAD_REQUEST.value()) {
			return new BadRequestException(responseBody);
		}

		if (response.status() == HttpStatus.NOT_FOUND.value()) {
			return new NotFoundException(responseBody);
		}

		return new UnhandledException(String.format("Unhandled exception [code: %s, msg: %s]", response.status(), responseBody));
	}

	private String getResponseBodyAsString(Response response) {
		if (response.body() == null) {
			return null;
		} else {
			try {
				Reader reader = response.body().asReader();
				if (!(reader).markSupported()) {
					reader = new BufferedReader(reader, 1);
				}
				reader.mark(1);
				if (reader.read() == -1) {
					return null;
				} else {
					reader.reset();
					JsonNode responseBody = CUSTOM_JACKSON_DECODER.mapper.readTree(reader);
					Optional<String> responseMessage = Optional.ofNullable(responseBody.get("message")).map(JsonNode::asText);
					return responseMessage.orElseGet(responseBody::toString);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
