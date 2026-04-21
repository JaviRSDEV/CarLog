package com.carlog.backend.error;

public class UserAlreadyInWorkshopException extends RuntimeException {
    public UserAlreadyInWorkshopException(String message) {
        super(message);
    }
}
