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
    private IdentificationTypeJpaEntity identifycationType;

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

    public IdentificationTypeJpaEntity getIdentifycationType() {
        return identifycationType;
    }

    private void setIdentifycationType(IdentificationTypeJpaEntity identifycationType) {
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
