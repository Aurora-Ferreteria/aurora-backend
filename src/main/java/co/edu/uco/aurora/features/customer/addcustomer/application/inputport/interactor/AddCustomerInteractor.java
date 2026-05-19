package co.edu.uco.aurora.features.customer.addcustomer.application.inputport.interactor;

import co.edu.uco.aurora.crosscutting.sanitizer.TextSanitizer;
import co.edu.uco.aurora.features.customer.addcustomer.application.inputport.AddCustomerInputPort;
import co.edu.uco.aurora.features.customer.addcustomer.application.inputport.dto.AddCustomerDTO;
import co.edu.uco.aurora.features.customer.addcustomer.application.inputport.interactor.mapper.AddCustomerDTOMapper;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.AddCustomerUseCase;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.AddCustomerDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AddCustomerInteractor implements AddCustomerInputPort {

    private final AddCustomerUseCase useCase;
    private final AddCustomerDTOMapper mapper;
    private final TextSanitizer sanitizer;

    public AddCustomerInteractor(AddCustomerUseCase useCase, AddCustomerDTOMapper mapper, TextSanitizer sanitizer) {
        this.useCase = useCase;
        this.mapper = mapper;
        this.sanitizer = sanitizer;
    }

    @Override
    public Void execute(AddCustomerDTO data) {

        data.setIdentificationNumber(sanitizer.sanitize(data.getIdentificationNumber()));
        data.setFullName(sanitizer.sanitize(data.getFullName()));
        data.setPhoneNumber(sanitizer.sanitize(data.getPhoneNumber()));
        data.setEmail(sanitizer.sanitize(data.getEmail()));

        // mapper de dto --> domain
        AddCustomerDomain domain = mapper.toDomain(data);
        return useCase.execute(domain);
    }
}
