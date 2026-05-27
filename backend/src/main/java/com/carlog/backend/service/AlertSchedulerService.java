package com.carlog.backend.service;

import com.carlog.backend.model.Alert;
import com.carlog.backend.repository.AlertJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertSchedulerService {

    private final AlertJpaRepository alertJpaRepository;
    private final MailService mailService;
    private final SpringTemplateEngine springTemplateEngine;

    @Scheduled(cron = "0 0 8 * * ?")
    @Transactional
    public void checkAndSendAlerts() {
        LocalDate today = LocalDate.now();
        LocalDate oneWeekFromNow = today.plusDays(7);

        log.info("Iniciando escaneo diario de alertas automatizadas...");

        List<Alert> warningAlerts = alertJpaRepository.findByDueDateAndNotifiedOneWeekFalse(oneWeekFromNow);
        for (Alert alert : warningAlerts) {
            enviarCorreoAlerta(alert, "Aviso: Te queda 1 semana para " + alert.getTitle());
            alert.setNotifiedOneWeek(true);
            alertJpaRepository.save(alert);
        }

        List<Alert> criticalAlerts = alertJpaRepository.findByDueDateAndNotifiedTodayFalse(today);
        for (Alert alert : criticalAlerts) {
            enviarCorreoAlerta(alert, "Recordatorio CarLog: Hoy es el día estipulado para " + alert.getTitle());
            alert.setNotifiedToday(true);
            alertJpaRepository.save(alert);
        }
    }

    private void enviarCorreoAlerta(Alert alert, String subject) {
        Context context = new Context();
        context.setVariable("username", alert.getUser().getName());
        context.setVariable("title", alert.getTitle());
        context.setVariable("description", alert.getDescription());
        context.setVariable("plate", alert.getVehicle().getPlate());
        context.setVariable("dueDate", alert.getDueDate().toString());

        try {
            String htmlContent = springTemplateEngine.process("emails/alert-notification", context);
            mailService.sendHtmlEmail(alert.getUser().getEmail(), subject, htmlContent);
            log.info("Alerta de correo enviada a: {} para el evento: {}", alert.getUser().getEmail(), alert.getTitle());
        } catch (Exception e) {
            log.error("Error al procesar email de alerta automatizada: {}", e.getMessage());
        }
    }
}