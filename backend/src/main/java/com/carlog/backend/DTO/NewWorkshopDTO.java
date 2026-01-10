package com.carlog.backend.DTO;

import com.carlog.backend.model.Workshop;

public record NewWorkshopDTO(String workshopName,
                             String address,
                             String workshopPhone,
                             String workshopEmail,
                             String icon) {

    public static NewWorkshopDTO of(Workshop w){
        return new NewWorkshopDTO(
                w.getWorkshopName(),
                w.getAddress(),
                w.getWorkshopPhone(),
                w.getWorkshopEmail(),
                w.getIcon()
        );}

}
