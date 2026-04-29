package com.carlog.backend.dto;

import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;

public record WorkshopHiringEvent(
        String userEmail,
        String userName,
        String workshopName,
        Role roleName
) {
    public static WorkshopHiringEvent of(User user) {
        return new WorkshopHiringEvent(
                user.getEmail(),
                user.getName(),
                user.getPendingWorkshop().getWorkshopName(),
                user.getPendingRole()
        );
    }
}
