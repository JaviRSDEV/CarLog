package com.carlog.backend.error;

public class ClosedWorkOrderException extends RuntimeException {
    public ClosedWorkOrderException(String message) {
        super(message);
    }
}
