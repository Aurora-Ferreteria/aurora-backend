package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain;

import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.validator.ValidateCustomerEmail;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.validator.ValidateCustomerFullName;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.validator.ValidateCustomerIdentificationNumber;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.validator.ValidateCustomerIdentificationType;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.validator.ValidateCustomerPhoneNumber;

import java.util.UUID;

public final class AddCustomerDomain {

    private UUID identificationType;
    private String identificationNumber;
    private String fullName;
    private String phoneNumber;
    private String email;

    public AddCustomerDomain(UUID identificationType, String identificationNumber, String fullName,
                             String phoneNumber, String email) {
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
        this.identificationType = ValidateCustomerIdentificationType.executeValidation(identificationType);
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    private void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = ValidateCustomerIdentificationNumber.executeValidation(identificationNumber);
    }

    public String getFullName() {
        return fullName;
    }

    private void setFullName(String fullName) {
        this.fullName = ValidateCustomerFullName.executeValidation(fullName);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = ValidateCustomerPhoneNumber.executeValidation(phoneNumber);
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = ValidateCustomerEmail.executeValidation(email);
    }
}