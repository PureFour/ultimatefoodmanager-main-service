package com.purefour.mainservice.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ConflictException extends Exception {
	public ConflictException(String msg) {
		super(msg);
	}
}
