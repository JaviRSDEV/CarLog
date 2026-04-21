package com.carlog.backend.error;

public class NoPendingRequestException extends RuntimeException {
    public NoPendingRequestException(String message) {
        super(message);
    }
}
