package co.edu.uco.aurora.features.customer.findcustomer.application.inputport.impl;

import co.edu.uco.aurora.features.customer.findcustomer.application.inputport.FindCustomerInputPort;
import co.edu.uco.aurora.features.customer.findcustomer.application.inputport.dto.FindCustomerDTO;
import co.edu.uco.aurora.features.customer.findcustomer.application.inputport.impl.mapper.FindCustomerDTOMapper;
import co.edu.uco.aurora.features.customer.findcustomer.application.usecase.FindCustomerUseCase;
import co.edu.uco.aurora.features.customer.findcustomer.application.usecase.domain.FindCustomerDomain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FindCustomerInteractor implements FindCustomerInputPort {

    private final FindCustomerUseCase useCase;
    private final FindCustomerDTOMapper mapper;

    public FindCustomerInteractor(FindCustomerUseCase useCase, FindCustomerDTOMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @Override
    public List<FindCustomerDTO> execute(Void data) {
        List<FindCustomerDomain> domains = useCase.execute(null);
        return mapper.toDTOs(domains);
    }
}