package edu.education.birdmanagementapi.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción para conflictos de datos (409).
 */
public class ConflictException extends ApiException{

    public ConflictException(String message) {

        super(message, HttpStatus.CONFLICT, "CONFLICT");
    }
}
