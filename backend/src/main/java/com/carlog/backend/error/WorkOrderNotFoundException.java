package com.carlog.backend.error;

public class WorkOrderNotFoundException extends RuntimeException {
    public WorkOrderNotFoundException() {

        super("No hay ordenes de trabajo con esos requisitos de búsqueda");
    }
}
