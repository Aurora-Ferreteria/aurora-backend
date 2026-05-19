package co.edu.uco.aurora.infrastructure.persistence.repository.sql.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table (name = "Clientes")
public class CustomerJpaEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "TipoIdentificacion")
    private IdentificationTypeJpaEntity identificationType;

    @Column(name = "NumeroIdentificacion")
    private String identificationNumber;

    @Column(name = "Nombre")
    private String fullName;

    @Column(name = "NumeroTelefono")
    private String phoneNumber;

    @Column(name = "Correo")
    private String email;

    public CustomerJpaEntity() {
        super();
    }

    public CustomerJpaEntity(UUID id, IdentificationTypeJpaEntity identifycationType, String identificationNumber,
                             String fullName, String phoneNumber, String email) {
        super();
        setId(id);
        setIdentificationType(identifycationType);
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

    public IdentificationTypeJpaEntity getIdentificationType() {
        return identificationType;
    }

     void setIdentificationType(IdentificationTypeJpaEntity identificationType) {
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
