package com.project.restapi.exceptions;

public class AssociatedNameAlreadyRegisteredException extends RuntimeException {

    public AssociatedNameAlreadyRegisteredException(String message) {
        super(message);
    }

    public AssociatedNameAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
