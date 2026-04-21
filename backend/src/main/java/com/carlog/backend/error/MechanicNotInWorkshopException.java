package com.carlog.backend.error;

public class MechanicNotInWorkshopException extends RuntimeException {
    public MechanicNotInWorkshopException(String message) {
        super(message);
    }
}
