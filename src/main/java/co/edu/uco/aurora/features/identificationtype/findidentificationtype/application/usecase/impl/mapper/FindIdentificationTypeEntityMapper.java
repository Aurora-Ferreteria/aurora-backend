package co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.usecase.impl.mapper;

import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.usecase.domain.FindIdentificationTypeDomain;
import co.edu.uco.aurora.infrastructure.persistence.repository.entity.IdentificationTypeEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FindIdentificationTypeEntityMapper {
    FindIdentificationTypeDomain toDomain(IdentificationTypeEntity entity);

    List<FindIdentificationTypeDomain> toDomains(List<IdentificationTypeEntity> entities);
}