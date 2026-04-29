package com.carlog.backend.listener;

import com.carlog.backend.dto.VehicleAdmissionEvent;
import com.carlog.backend.dto.WorkshopHiringEvent;
import com.carlog.backend.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final MailService mailService;

    @Async
    @EventListener
    public void handleVehicleAdmissionEvent(VehicleAdmissionEvent event){
        log.info("Capturado evento de admisión: Enviando correo a {}", event.clientEmail());
        mailService.sendVehicleAdmissionEmail(
                event.clientEmail(),
                event.clientName(),
                event.vehiclePlate(),
                event.workshopName()
        );
    }

    @Async
    @EventListener
    public void handleWorkshopHiringEvent(WorkshopHiringEvent event) {
        log.info("Capturado evento de contratación: Enviando correo a {}", event.userEmail());
        mailService.sendHiringMessage(
                event.userEmail(),
                event.userName(),
                event.workshopName(),
                event.roleName().name()
        );
    }
}
