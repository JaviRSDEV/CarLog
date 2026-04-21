package com.carlog.backend.error;

public class VehicleAlreadyExistsException extends RuntimeException {
    public VehicleAlreadyExistsException(String message) {
        super(message);
    }
}
