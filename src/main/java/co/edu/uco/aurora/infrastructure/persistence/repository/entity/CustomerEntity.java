package co.edu.uco.aurora.infrastructure.persistence.repository.entity;

import java.util.UUID;

public class CustomerEntity {

    private UUID id;
    private IdentificationTypeEntity identifycationType;
    private String identificationNumber;
    private String fullName;
    private String phoneNumber;
    private String email;

    public CustomerEntity() {
        super();
    }

    public CustomerEntity(UUID id, IdentificationTypeEntity identifycationType, String identificationNumber,
                          String fullName, String phoneNumber, String email) {
        super();
        setId(id);
        setIdentifycationType(identifycationType);
        setIdentificationNumber(identificationNumber);
        setFullName(fullName);
        setPhoneNumber(phoneNumber);
        setEmail(email);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public IdentificationTypeEntity getIdentifycationType() {
        return identifycationType;
    }

    public void setIdentifycationType(IdentificationTypeEntity identifycationType) {
        this.identifycationType = identifycationType;
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
