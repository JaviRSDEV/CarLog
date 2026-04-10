package com.carlog.backend.dto;

import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;

public record NewWorkshopDTO(
                             Long workshopId,
                             String workshopName,
                             String address,
                             String workshopPhone,
                             String workshopEmail,
                             String icon
                             ) {

    public static NewWorkshopDTO of(Workshop w){
        return new NewWorkshopDTO(
                w.getWorkshopId(),
                w.getWorkshopName(),
                w.getAddress(),
                w.getWorkshopPhone(),
                w.getWorkshopEmail(),
                w.getIcon()
        );}

}
