package co.edu.uco.aurora.infrastructure.persistence.repository.entity;

import java.io.Serializable;
import java.util.UUID;

public class CustomerEntity implements Serializable {

    private UUID id;
    private IdentificationTypeEntity identificationType;
    private String identificationNumber;
    private String fullName;
    private String phoneNumber;
    private String email;

    public CustomerEntity() {
        super();
    }

    public CustomerEntity(UUID id, IdentificationTypeEntity identificationType, String identificationNumber,
                          String fullName, String phoneNumber, String email) {
        super();
        setId(id);
        setIdentificationType(identificationType);
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

    public IdentificationTypeEntity getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(IdentificationTypeEntity identificationType) {
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
