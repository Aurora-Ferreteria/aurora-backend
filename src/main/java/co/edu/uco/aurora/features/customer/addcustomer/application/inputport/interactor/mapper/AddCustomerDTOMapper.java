package co.edu.uco.aurora.features.customer.addcustomer.application.inputport.interactor.mapper;

import co.edu.uco.aurora.features.customer.addcustomer.application.inputport.dto.AddCustomerDTO;
import co.edu.uco.aurora.application.inputport.impl.mapper.DTOMapper;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.AddCustomerDomain;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddCustomerDTOMapper extends DTOMapper<AddCustomerDTO, AddCustomerDomain> {
}