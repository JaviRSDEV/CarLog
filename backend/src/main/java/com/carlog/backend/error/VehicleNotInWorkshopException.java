package com.carlog.backend.error;

public class VehicleNotInWorkshopException extends RuntimeException {
    public VehicleNotInWorkshopException(String message) {
        super(message);
    }
}
