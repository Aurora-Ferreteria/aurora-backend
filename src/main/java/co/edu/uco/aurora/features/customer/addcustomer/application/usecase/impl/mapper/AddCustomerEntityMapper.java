package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.impl.mapper;

import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.AddCustomerDomain;
import co.edu.uco.aurora.application.usecase.impl.mapper.EntityMapper;
import co.edu.uco.aurora.infrastructure.persistence.repository.entity.CustomerEntity;
import co.edu.uco.aurora.infrastructure.persistence.repository.entity.IdentificationTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AddCustomerEntityMapper extends EntityMapper<CustomerEntity, AddCustomerDomain> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "identifycationType", source = "identificationType")
    CustomerEntity toEntity(AddCustomerDomain domain);

    @Override
    @Mapping(target = "identificationType", source = "identifycationType")
    AddCustomerDomain toDomain(CustomerEntity entity);

    default IdentificationTypeEntity map(UUID id) {
        if (id == null) {
            return null;
        }
        IdentificationTypeEntity entity = new IdentificationTypeEntity();
        entity.setId(id);
        return entity;
    }

    default UUID map(IdentificationTypeEntity entity) {
        if (entity == null) {
            return null;
        }
        return entity.getId();
    }
}
