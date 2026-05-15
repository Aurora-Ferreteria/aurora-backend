package co.edu.uco.aurora.features.customer.addcustomer.application.inputport.impl;

import co.edu.uco.aurora.features.customer.addcustomer.application.inputport.AddCustomerInputPort;
import co.edu.uco.aurora.features.customer.addcustomer.application.inputport.dto.AddCustomerDTO;
import co.edu.uco.aurora.features.customer.addcustomer.application.inputport.impl.mapper.AddCustomerDTOMapper;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.AddCustomerUseCase;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.AddCustomerDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AddCustomerInteractor implements AddCustomerInputPort {

    private AddCustomerUseCase useCase;
    private AddCustomerDTOMapper mapper;

    public AddCustomerInteractor(AddCustomerUseCase useCase, AddCustomerDTOMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @Override
    public Void execute(AddCustomerDTO data) {
        // mapper de dto --> domain
        AddCustomerDomain domain = mapper.toDomain(data);
        return useCase.execute(domain);
    }
}
