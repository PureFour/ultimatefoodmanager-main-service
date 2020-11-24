package com.purefour.mainservice.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UnhandledException extends Exception {
	public UnhandledException(String msg) {
		super(msg);
	}
}
