package co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.inputport.dto;

import java.util.UUID;

public final class FindIdentificationTypeDTO {

    private UUID id;
    private String name;

    public FindIdentificationTypeDTO() {
        super();
    }

    public FindIdentificationTypeDTO(UUID id, String name) {
        super();
        this.id = id;
        this.name = name;
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

    public void setName(String name) {
        this.name = name;
    }
}