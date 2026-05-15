package co.edu.uco.aurora.features.customer.findcustomer.application.inputport.impl.mapper;

import co.edu.uco.aurora.application.inputport.impl.mapper.DTOMapper;
import co.edu.uco.aurora.features.customer.findcustomer.application.inputport.dto.FindCustomerDTO;
import co.edu.uco.aurora.features.customer.findcustomer.application.usecase.domain.FindCustomerDomain;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FindCustomerDTOMapper extends DTOMapper<FindCustomerDTO, FindCustomerDomain> {

    List<FindCustomerDTO> toDTOs(List<FindCustomerDomain> domains);

}