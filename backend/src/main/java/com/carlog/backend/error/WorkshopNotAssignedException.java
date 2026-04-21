package com.carlog.backend.error;

public class WorkshopNotAssignedException extends RuntimeException {
    public WorkshopNotAssignedException(String message) {
        super(message);
    }
}
