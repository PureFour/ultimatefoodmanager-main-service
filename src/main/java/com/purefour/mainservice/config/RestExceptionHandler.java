package com.purefour.mainservice.config;

import com.purefour.mainservice.model.exceptions.BadRequestException;
import com.purefour.mainservice.model.exceptions.ConflictException;
import com.purefour.mainservice.model.exceptions.ErrorMessage;
import com.purefour.mainservice.model.exceptions.NotFoundException;
import com.purefour.mainservice.model.exceptions.UnauthorizedException;
import com.purefour.mainservice.model.exceptions.UnhandledException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler({ NotFoundException.class })
	protected ResponseEntity<ErrorMessage> notFound(Exception exception) {
		return errorResponse(HttpStatus.NOT_FOUND, exception);
	}

	@ExceptionHandler({ ConflictException.class })
	protected ResponseEntity<ErrorMessage> conflict(Exception exception) {
		return errorResponse(HttpStatus.CONFLICT, exception);
	}

	@ExceptionHandler({ BadRequestException.class })
	protected ResponseEntity<ErrorMessage> badRequest(Exception exception) {
		return errorResponse(HttpStatus.BAD_REQUEST, exception);
	}

	@ExceptionHandler({ UnauthorizedException.class })
	protected ResponseEntity<ErrorMessage> unauthorized(Exception exception) {
		return errorResponse(HttpStatus.UNAUTHORIZED, exception);
	}

	@ExceptionHandler({ ConnectException.class })
	protected ResponseEntity<ErrorMessage> connectionError(Exception exception) {
		return errorResponse(HttpStatus.SERVICE_UNAVAILABLE, exception);
	}

	@ExceptionHandler({ UnhandledException.class })
	protected ResponseEntity<ErrorMessage> unhandledException(Exception exception) {
		return errorResponse(HttpStatus.valueOf(420), exception);
	}

	private ResponseEntity<ErrorMessage> errorResponse(HttpStatus status, Exception exception) {
		final String exceptionMessage = Objects.isNull(exception.getCause()) ? exception.getMessage() : exception.getCause().getMessage();
		return ResponseEntity.status(status).body(new ErrorMessage(exceptionMessage));
	}
}
