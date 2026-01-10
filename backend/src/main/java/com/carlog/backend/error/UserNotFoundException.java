package com.carlog.backend.error;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String dni) {

        super("No hay un usuario con DNI " + dni);
    }

    public UserNotFoundException(){
        super("No hay usuarios con esos requisitos de búsqueda");
    }
}
