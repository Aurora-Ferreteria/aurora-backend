package co.edu.uco.aurora.features.customer.findcustomer.application.usecase.domain;

import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.usecase.domain.FindIdentificationTypeDomain;

import java.util.UUID;

public final class FindCustomerDomain {

    private UUID id;
    private FindIdentificationTypeDomain identificationType;
    private String identificationNumber;
    private String fullName;
    private String phoneNumber;
    private String email;

    public FindCustomerDomain() {
        super();
    }

    public FindCustomerDomain(UUID id, FindIdentificationTypeDomain identificationType, String identificationNumber, String fullName, String phoneNumber, String email) {
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

    public FindIdentificationTypeDomain getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(FindIdentificationTypeDomain identificationType) {
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