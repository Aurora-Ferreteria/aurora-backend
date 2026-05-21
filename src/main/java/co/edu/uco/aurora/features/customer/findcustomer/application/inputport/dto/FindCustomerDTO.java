package co.edu.uco.aurora.features.customer.findcustomer.application.inputport.dto;

import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.inputport.dto.FindIdentificationTypeDTO;

import java.util.UUID;

public final class FindCustomerDTO {

    private UUID id;
    private FindIdentificationTypeDTO identificationType;
    private String identificationNumber;
    private String fullName;
    private String phoneNumber;
    private String email;

    public FindCustomerDTO() {
        super();
    }

    public FindCustomerDTO(UUID id, FindIdentificationTypeDTO identificationType, String identificationNumber, String fullName, String phoneNumber, String email) {
        super();
        this.id = id;
        this.identificationType = identificationType;
        this.identificationNumber = identificationNumber;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FindIdentificationTypeDTO getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(FindIdentificationTypeDTO identificationType) {
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