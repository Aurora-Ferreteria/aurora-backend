package co.edu.uco.aurora.infrastructure.persistence.repository.sql.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "TiposIdentificacion")
public class IdentifycationTypeJpaEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre")
    private String name;

    public IdentifycationTypeJpaEntity() {
        super();
    }

    public IdentifycationTypeJpaEntity(UUID id, String name) {
        super();
        setId(id);
        setName(name);
    }

    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

}
