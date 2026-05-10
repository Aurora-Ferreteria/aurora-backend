package co.edu.uco.aurora.features.customer.addcustomer.application.inputport.dto;

import java.util.UUID;

public final class AddCustomerDTO {

    private UUID identificationType;
    private String identificationNumber;
    private String fullName;
    private String phoneNumber;
    private String email;

    public AddCustomerDTO(UUID identificationType, String identificationNumber, String fullName, String phoneNumber, String email) {
        super();
        setIdentificationType(identificationType);
        setIdentificationNumber(identificationNumber);
        setFullName(fullName);
        setPhoneNumber(phoneNumber);
        setEmail(email);
    }

    public UUID getIdentificationType() {
        return identificationType;
    }

    private void setIdentificationType(UUID identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    private void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getFullName() {
        return fullName;
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }
}
