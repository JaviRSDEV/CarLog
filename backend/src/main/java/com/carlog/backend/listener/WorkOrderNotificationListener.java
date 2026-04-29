package com.carlog.backend.listener;

import com.carlog.backend.dto.WorkOrderCompletedEvent;
import com.carlog.backend.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkOrderNotificationListener {
    private final MailService mailService;

    @Async
    @EventListener
    public void onWorkOrderCompleted(WorkOrderCompletedEvent event){
        log.info("Evento capturado: Iniciando envío de correo para la matrícula {}", event.vehiclePlate());
        mailService.sendWorkOrderCompletedEmail(event.clientEmail(), event.clientName(), event.vehiclePlate());
    }
}
