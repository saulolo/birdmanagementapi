package edu.education.birdmanagementapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Excepción base para todas las excepciones personalizadas de la API.
 */
@Getter
public abstract class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;

    protected ApiException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

}
