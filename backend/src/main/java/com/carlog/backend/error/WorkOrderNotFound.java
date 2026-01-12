package com.carlog.backend.error;

public class WorkOrderNotFound extends RuntimeException {
    public WorkOrderNotFound(String message) {
        super(message);
    }
}
