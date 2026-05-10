package co.edu.uco.aurora.infrastructure.persistence.repository.sql.jpa;

import co.edu.uco.aurora.infrastructure.persistence.repository.sql.jpa.entity.IdentifycationTypeJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IdentifycationTypeJPARepository extends JpaRepository <IdentifycationTypeJPAEntity, UUID> {
}
