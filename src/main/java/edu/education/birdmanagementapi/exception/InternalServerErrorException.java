package edu.education.birdmanagementapi.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends ApiException{

    public InternalServerErrorException(String message) {

        super(message, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR");
    }
}
