package co.edu.uco.aurora.infrastructure.persistence.repository.adapter.sql.jpa.mapper.customer;

import co.edu.uco.aurora.infrastructure.persistence.repository.adapter.sql.jpa.mapper.JPAMapper;
import co.edu.uco.aurora.infrastructure.persistence.repository.entity.CustomerEntity;
import co.edu.uco.aurora.infrastructure.persistence.repository.sql.jpa.entity.CustomerJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerJpaMapper extends JPAMapper <CustomerEntity, CustomerJpaEntity> {
}
