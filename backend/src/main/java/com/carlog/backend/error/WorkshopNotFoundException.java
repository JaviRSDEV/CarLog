package com.carlog.backend.error;

public class WorkshopNotFoundException extends RuntimeException {
    public WorkshopNotFoundException(Long id) {

        super("No hay un taller con id " + id);
    }

    public WorkshopNotFoundException(){
        super("No hay usuarios con esos requisitos de búsqueda");
    }
}
