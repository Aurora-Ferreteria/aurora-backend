package co.edu.uco.aurora.infrastructure.persistence.repository.adapter.sql.jpa;

import co.edu.uco.aurora.infrastructure.persistence.repository.IdentificationTypeRepository;
import co.edu.uco.aurora.infrastructure.persistence.repository.adapter.sql.jpa.mapper.identificationTypeJpaMapper.IdentificationTypeJpaMapper;
import co.edu.uco.aurora.infrastructure.persistence.repository.entity.IdentificationTypeEntity;
import co.edu.uco.aurora.infrastructure.persistence.repository.sql.jpa.IdentificationTypeJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class IdentificationTypeJpaRepositoryInteractor implements IdentificationTypeRepository {

    private final IdentificationTypeJpaRepository repository;
    private final IdentificationTypeJpaMapper mapper;

    public IdentificationTypeJpaRepositoryInteractor(IdentificationTypeJpaRepository repository, IdentificationTypeJpaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<IdentificationTypeEntity> findAll() {
        return List.of();
    }

    @Override
    public IdentificationTypeEntity findById(IdentificationTypeEntity filter) {
        return null;
    }

    @Override
    public IdentificationTypeEntity findById(UUID id) {
        return null;
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
}
