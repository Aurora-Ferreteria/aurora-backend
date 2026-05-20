package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.impl;

public interface WelcomeEmailSender {
    void sendWelcomeEmail(String toEmail, String customerName);
}
