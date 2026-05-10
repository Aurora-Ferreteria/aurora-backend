package co.edu.uco.aurora.infrastructure.persistence.repository.sql.jpa;

import co.edu.uco.aurora.infrastructure.persistence.repository.sql.jpa.entity.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, UUID> {
}

