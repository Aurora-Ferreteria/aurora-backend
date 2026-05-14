package co.edu.uco.aurora.features.customer.addcustomer.application.inputport.dto;

import java.util.UUID;

public final class AddCustomerDTO {

    private UUID identificationType;
    private String identificationNumber;
    private String fullName;
    private String phoneNumber;
    private String email;

    public AddCustomerDTO() {
        super();
    }

    public AddCustomerDTO(UUID identificationType, String identificationNumber, String fullName, String phoneNumber, String email) {
        super();
        this.identificationType = identificationType;
        this.identificationNumber = identificationNumber;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public UUID getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(UUID identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}