package co.edu.uco.aurora.infrastructure.persistence.repository.adapter.sql.jpa.mapper.customer;

import co.edu.uco.aurora.infrastructure.persistence.repository.adapter.sql.jpa.mapper.JPAMapper;
import co.edu.uco.aurora.infrastructure.persistence.repository.adapter.sql.jpa.mapper.identificationtype.IdentificationTypeJpaMapper;
import co.edu.uco.aurora.infrastructure.persistence.repository.entity.CustomerEntity;
import co.edu.uco.aurora.infrastructure.persistence.repository.sql.jpa.entity.CustomerJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { IdentificationTypeJpaMapper.class })
public interface CustomerJpaMapper extends JPAMapper <CustomerEntity, CustomerJpaEntity> {

}