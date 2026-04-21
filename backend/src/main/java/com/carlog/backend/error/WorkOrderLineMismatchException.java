package com.carlog.backend.error;

public class WorkOrderLineMismatchException extends RuntimeException {
    public WorkOrderLineMismatchException(String message) {
        super(message);
    }
}
