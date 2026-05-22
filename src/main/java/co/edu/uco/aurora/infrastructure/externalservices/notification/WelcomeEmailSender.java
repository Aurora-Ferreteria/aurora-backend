package co.edu.uco.aurora.infrastructure.externalservices.notification;

import co.edu.uco.aurora.infrastructure.externalservices.notification.dto.WelcomeEmailDTO;

public interface WelcomeEmailSender {
    void sendWelcomeEmail(WelcomeEmailDTO emailDto);
}