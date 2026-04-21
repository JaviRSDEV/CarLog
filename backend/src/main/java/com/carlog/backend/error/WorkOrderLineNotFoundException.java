package com.carlog.backend.error;

public class WorkOrderLineNotFoundException extends RuntimeException {
    public WorkOrderLineNotFoundException(String message) {
        super(message);
    }
}
