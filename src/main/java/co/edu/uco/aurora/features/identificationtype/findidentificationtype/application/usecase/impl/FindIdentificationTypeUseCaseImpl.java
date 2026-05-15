package co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.usecase.impl;

import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.usecase.FindIdentificationTypeUseCase;
import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.usecase.domain.FindIdentificationTypeDomain;
import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.usecase.impl.mapper.FindIdentificationTypeEntityMapper;
import co.edu.uco.aurora.infrastructure.persistence.repository.IdentificationTypeRepository;

import co.edu.uco.aurora.infrastructure.persistence.repository.entity.IdentificationTypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindIdentificationTypeUseCaseImpl implements FindIdentificationTypeUseCase {

    private final IdentificationTypeRepository repository;
    private final FindIdentificationTypeEntityMapper mapper;

    public FindIdentificationTypeUseCaseImpl(IdentificationTypeRepository repository, FindIdentificationTypeEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<FindIdentificationTypeDomain> execute(Void data) {
        List<IdentificationTypeEntity> entities = repository.findAll();
        return mapper.toDomains(entities);
    }
}