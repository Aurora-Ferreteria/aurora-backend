package co.edu.uco.aurora.application.usecase;

public interface WelcomeEmailSender {
    void sendWelcomeEmail(String toEmail, String customerName);
}
