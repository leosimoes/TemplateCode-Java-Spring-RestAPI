package com.project.restapi.exceptions;

public class AssociatedNotFoundException extends RuntimeException {

    public AssociatedNotFoundException(String message) {
        super(message);
    }

    public AssociatedNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
