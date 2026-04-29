package com.carlog.backend.service;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private SpringTemplateEngine templateEngine;

    @InjectMocks
    private MailService mailService;

    private MimeMessage mimeMessage;

    @BeforeEach
    void setUp() {
        mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    void sendWorkOrderCompletedEmail_Success() {
        String email = "test@client.com";
        String name = "Juan";
        String plate = "1234ABC";

        when(templateEngine.process(eq("emails/work-order-completed"), any(Context.class)))
                .thenReturn("<html>Email Content</html>");

        mailService.sendWorkOrderCompletedEmail(email, name, plate);

        verify(templateEngine).process(eq("emails/work-order-completed"), any(Context.class));
        verify(mailSender).send(mimeMessage);
    }

    @Test
    void sendVehicleAdmissionEmail_Success() {
        String email = "test@client.com";
        when(templateEngine.process(eq("emails/vehicle-admission"), any(Context.class)))
                .thenReturn("<html>Admission Content</html>");

        mailService.sendVehicleAdmissionEmail(email, "Juan", "1234ABC", "Taller Central");

        ArgumentCaptor<Context> contextCaptor = ArgumentCaptor.forClass(Context.class);
        verify(templateEngine).process(eq("emails/vehicle-admission"), contextCaptor.capture());

        Context context = contextCaptor.getValue();
        assertEquals("1234ABC", context.getVariable("plate")); // Verifica que se pasó la matrícula

        verify(mailSender).send(mimeMessage);
    }

    @Test
    void sendHiringMessage_Success() {
        when(templateEngine.process(eq("emails/hiring-message"), any(Context.class)))
                .thenReturn("<html>Hiring Content</html>");

        mailService.sendHiringMessage("user@test.com", "Pedro", "Taller 1", "MECHANIC");

        verify(templateEngine).process(eq("emails/hiring-message"), any(Context.class));
        verify(mailSender).send(mimeMessage);
    }

    @Test
    void sendWorkOrderCompletedEmail_HandleException() {
        when(templateEngine.process(anyString(), any(Context.class)))
                .thenThrow(new RuntimeException("Simulated Mail Error"));

        assertDoesNotThrow(() ->
                mailService.sendWorkOrderCompletedEmail("test@test.com", "User", "1234ABC")
        );

        verify(mailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void sendVehicleAdmissionEmail_HandleMessagingException() {
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn("content");
        doThrow(new RuntimeException("Connection error")).when(mailSender).send(any(MimeMessage.class));

        assertDoesNotThrow(() ->
                mailService.sendVehicleAdmissionEmail("a@a.com", "N", "P", "W")
        );
    }
}