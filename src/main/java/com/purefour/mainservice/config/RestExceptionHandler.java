package com.purefour.mainservice.config;

import com.purefour.mainservice.model.ErrorMessage;
import com.purefour.mainservice.model.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler({ NotFoundException.class })
	protected ResponseEntity<ErrorMessage> notFound(Exception exception) {
		return errorResponse(HttpStatus.NOT_FOUND, exception);
	}

	private ResponseEntity<ErrorMessage> errorResponse(HttpStatus status, Exception exception) {
		return ResponseEntity.status(status).body(new ErrorMessage(exception.getCause().getMessage()));
	}
}
