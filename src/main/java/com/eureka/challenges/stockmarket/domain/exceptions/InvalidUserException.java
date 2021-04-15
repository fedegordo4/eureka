package com.eureka.challenges.stockmarket.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.INTERNAL_SERVER_ERROR,
        reason = "Invalid user format"
)
public class InvalidUserException extends RuntimeException {

    public InvalidUserException(String message) {
        super(message);
    }

}
