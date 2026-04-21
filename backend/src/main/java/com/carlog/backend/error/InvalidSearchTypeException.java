package com.carlog.backend.error;

public class InvalidSearchTypeException extends RuntimeException {
    public InvalidSearchTypeException(String message) {
        super(message);
    }
}
