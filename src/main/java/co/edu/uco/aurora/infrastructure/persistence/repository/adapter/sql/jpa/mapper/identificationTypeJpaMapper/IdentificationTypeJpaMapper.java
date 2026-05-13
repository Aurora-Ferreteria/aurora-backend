package co.edu.uco.aurora.infrastructure.persistence.repository.adapter.sql.jpa.mapper.identificationTypeJpaMapper;

import co.edu.uco.aurora.infrastructure.persistence.repository.adapter.sql.jpa.mapper.JPAMapper;
import co.edu.uco.aurora.infrastructure.persistence.repository.entity.IdentificationTypeEntity;
import co.edu.uco.aurora.infrastructure.persistence.repository.sql.jpa.entity.IdentificationTypeJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IdentificationTypeJpaMapper extends JPAMapper<IdentificationTypeEntity, IdentificationTypeJpaEntity> {



}
