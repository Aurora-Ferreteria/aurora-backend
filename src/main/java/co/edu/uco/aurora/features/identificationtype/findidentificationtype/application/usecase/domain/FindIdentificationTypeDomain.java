package co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.usecase.domain;

import java.util.UUID;

public final class FindIdentificationTypeDomain {

    private UUID id;
    private String name;

    public FindIdentificationTypeDomain() {
        super();
    }

    public FindIdentificationTypeDomain(UUID id, String name) {
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