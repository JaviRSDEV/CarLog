package com.carlog.backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendWorkOrderCompletedEmail(String clientEmail, String clientName, String vehiclePlate){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(clientEmail);
            helper.setSubject("¡Buenas noticias! Tu vehículo está listo - CarLog");

            Context context = new Context();
            context.setVariable("clientName", clientName);
            context.setVariable("vehiclePlate", vehiclePlate.toUpperCase());

            String htmlContent = templateEngine.process("emails/work-order-completed", context);

            helper.setText(htmlContent, true);
            mailSender.send(message);
            log.info("Email de orden completada enviado a {}", clientEmail);

        }catch (Exception e){
            log.error("Error enviando email a " + clientEmail + ": " + e.getMessage());
        }
    }

    @Async
    public void sendVehicleAdmissionEmail(String clientEmail, String clientName, String plate, String workshopName){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            Context context = new Context();
            context.setVariable("clientName", clientName);
            context.setVariable("workshopName", workshopName);
            context.setVariable("plate", plate.toUpperCase());

            String htmlContent = templateEngine.process("emails/vehicle-admission", context);

            helper.setTo(clientEmail);
            helper.setSubject("Solicitud de ingreso enviada - CarLog");
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Email de ingreso enviado a {}", clientEmail);
        }catch (Exception e){
            log.error("Error enviando el email de ingreso: {}", e.getMessage());
        }

    }

    @Async
    public void sendHiringMessage(String to, String userName, String workshopName, String roleName){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            Context context = new Context();
            context.setVariable("userName", userName);
            context.setVariable("workshopName", workshopName);
            context.setVariable("roleName", roleName);

            String htmlContent = templateEngine.process("emails/hiring-message", context);

            helper.setTo(to);
            helper.setSubject("Oferta de trabajo - CarLog");
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Email de contratación enviado a {}", to);
        }catch (Exception e){
            log.error("Error enviando el email de contratación: {}", e.getMessage());
        }
    }

}
