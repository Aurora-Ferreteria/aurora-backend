package co.edu.uco.aurora.features.customer.findcustomer.application.usecase.impl.mapper;

import co.edu.uco.aurora.features.customer.findcustomer.application.usecase.domain.FindCustomerDomain;
import co.edu.uco.aurora.infrastructure.persistence.repository.entity.CustomerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FindCustomerEntityMapper {

    FindCustomerDomain toDomain(CustomerEntity entity);

    List<FindCustomerDomain> toDomains(List<CustomerEntity> entities);
}