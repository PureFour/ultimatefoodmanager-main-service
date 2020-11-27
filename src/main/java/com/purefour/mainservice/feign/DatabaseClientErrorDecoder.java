package com.purefour.mainservice.feign;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.purefour.mainservice.model.exceptions.BadRequestException;
import com.purefour.mainservice.model.exceptions.NotFoundException;
import com.purefour.mainservice.model.exceptions.UnhandledException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

@Slf4j
public class DatabaseClientErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {

		String responseBody = String.format("%s: %s", methodKey, getResponseBodyAsString(response.body()));

		switch (response.status()) {
			case 400:
				return new BadRequestException(responseBody);
			case 404:
				return new NotFoundException(responseBody);
			default:
				return new UnhandledException(String.format("Unhandled exception [code: %s, msg: %s]", response.status(), responseBody));
		}
	}

	private String getResponseBodyAsString(final Response.Body body) {
		try {
			return IOUtils.toString(body.asReader(StandardCharsets.UTF_8));
		} catch (final IOException e) {
			log.error("Failed to read the response body with error: ", e);
		}
		return null;
	}
}
