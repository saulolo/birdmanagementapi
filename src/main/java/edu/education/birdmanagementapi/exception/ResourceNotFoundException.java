package edu.education.birdmanagementapi.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción para recursos no encontrados (404).
 */
public class ResourceNotFoundException extends ApiException{

    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND");
    }
}
