package co.edu.uco.aurora.features.customer.addcustomer.application.inputport.mapper.customer;

import co.edu.uco.aurora.application.inputport.mapper.DTOMapper;
import co.edu.uco.aurora.features.customer.addcustomer.application.inputport.dto.AddCustomerDTO;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.AddCustomerDomain;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddCustomerMapper extends DTOMapper<AddCustomerDTO, AddCustomerDomain> {
}