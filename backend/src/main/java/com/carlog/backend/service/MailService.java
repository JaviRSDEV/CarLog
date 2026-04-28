package com.carlog.backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendWorkOrderCompletedEmail(String clientEmail, String clientName, String vehiclePlate){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(clientEmail);
            helper.setSubject("¡Buenas noticias! Tu vehículo está listo - CarLog");

            String htmlContent = String.format(
                    "<h1>Hola, %s</h1>" +
                            "<p>Te informamos que la orden de trabajo para el vehículo con matrícula <strong>%s</strong> ha sido completada con éxito.</p>" +
                            "<p>Ya puedes pasarte por el taller a recogerlo.</p>" +
                            "<br><p>Saludos,<br>El equipo de CarLog</p>",
                    clientName, vehiclePlate
            );

            helper.setText(htmlContent, true);
            mailSender.send(message);
        }catch (MessagingException e){
            log.error("Error enviando email a " + clientEmail + ": " + e.getMessage());
        }
    }
}
