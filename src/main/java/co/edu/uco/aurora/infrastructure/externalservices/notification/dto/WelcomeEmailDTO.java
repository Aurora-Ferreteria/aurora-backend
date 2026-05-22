package co.edu.uco.aurora.infrastructure.externalservices.notification.dto;

public class WelcomeEmailDTO {
    private String toEmail;
    private String customerName;

    public WelcomeEmailDTO(String toEmail, String customerName) {
        this.toEmail = toEmail;
        this.customerName = customerName;
    }
    public String getToEmail() { return toEmail; }
    public String getCustomerName() { return customerName; }
}