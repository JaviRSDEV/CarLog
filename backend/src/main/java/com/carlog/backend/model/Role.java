package com.carlog.backend.model;

public enum Role {

    MANAGER,
    CO_MANAGER,
    MECHANIC,
    CLIENT;

    public boolean isWorker(){
        return this == MANAGER || this == CO_MANAGER || this == MECHANIC;
    }
}
