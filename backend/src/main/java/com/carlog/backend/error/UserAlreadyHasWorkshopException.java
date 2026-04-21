package com.carlog.backend.error;

public class UserAlreadyHasWorkshopException extends RuntimeException {
    public UserAlreadyHasWorkshopException(String message) {
        super(message);
    }
}
