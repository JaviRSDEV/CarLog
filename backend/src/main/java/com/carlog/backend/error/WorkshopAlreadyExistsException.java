package com.carlog.backend.error;

public class WorkshopAlreadyExistsException extends RuntimeException {
    public WorkshopAlreadyExistsException(String message) {
        super(message);
    }
}
