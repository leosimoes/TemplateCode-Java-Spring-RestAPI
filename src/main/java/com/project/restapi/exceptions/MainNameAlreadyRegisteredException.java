package com.project.restapi.exceptions;

public class MainNameAlreadyRegisteredException extends RuntimeException {

    public MainNameAlreadyRegisteredException(String message) {
        super(message);
    }

    public MainNameAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
