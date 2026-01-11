package com.carlog.backend.error;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String matricula) {

        super("No hay un vehículo con con matricula " + matricula);
    }

    public VehicleNotFoundException(){
        super("No hay vehículos con esos requisitps de búsqueda");
    }
}
