package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.impl.mapper;

import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.AddCustomerDomain;
import co.edu.uco.aurora.infrastructure.externalservices.notification.dto.WelcomeEmailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddCustomerEmailMapper {

    @Mapping(target = "toEmail", source = "email")
    @Mapping(target = "customerName", source = "fullName")
    WelcomeEmailDTO toDto(AddCustomerDomain domain);
}