package com.project.restapi.exceptions;

public class MainNotFoundException extends RuntimeException {

    public MainNotFoundException(String message) {
        super(message);
    }

    public MainNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}