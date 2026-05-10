package co.edu.uco.aurora.infrastructure.persistence.repository;

import co.edu.uco.aurora.infrastructure.persistence.repository.sql.jpa.entity.IdentifycationTypeJPAEntity;

import java.util.List;
import java.util.UUID;

public interface IdentifycationTypeRepository {

    List<IdentifycationTypeJPAEntity> findAll();

    IdentifycationTypeJPAEntity findById(IdentifycationTypeJPAEntity filter);

    IdentifycationTypeJPAEntity findById(UUID id);

}
