package co.edu.uco.aurora.infrastructure.persistence.repository;

import co.edu.uco.aurora.infrastructure.persistence.repository.entity.IdentificationTypeEntity;

import java.util.List;
import java.util.UUID;

public interface IdentificationTypeRepository {

    List<IdentificationTypeEntity> findAll();

    IdentificationTypeEntity findByFilter(IdentificationTypeEntity filter);

    IdentificationTypeEntity findByFilter(UUID id);

    boolean existsById(UUID id);

}
