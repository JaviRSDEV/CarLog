package com.carlog.backend.error;

public class WorkshopNotFoundException extends RuntimeException {
    public WorkshopNotFoundException(String name) {

        super("No hay un taller con el nombre " + name);
    }

    public WorkshopNotFoundException(Long id) {

        super("No hay un taller con el id " + id);
    }

    public WorkshopNotFoundException(){
        super("No hay usuarios con esos requisitos de búsqueda");
    }
}
