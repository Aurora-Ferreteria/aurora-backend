package co.edu.uco.aurora.infrastructure.persistence.repository.entity;

import java.io.Serializable;
import java.util.UUID;

public class IdentificationTypeEntity implements Serializable {

    private UUID id;
    private String name;

    public IdentificationTypeEntity() {
        super();
    }

    public IdentificationTypeEntity(UUID id, String name) {
        super();
        setId(id);
        setName(name);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
}
