package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.impl;

import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.AddCustomerUseCase;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.AddCustomerDomain;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.impl.mapper.customer.AddCustomerEntityMapper;
import co.edu.uco.aurora.infrastructure.persistence.repository.CustomerRepository;
import co.edu.uco.aurora.infrastructure.persistence.repository.entity.CustomerEntity;
import org.springframework.stereotype.Service;

@Service
public class AddCustomerUseCaseImpl implements AddCustomerUseCase {

    private final CustomerRepository  customerRepository;
    private final AddCustomerEntityMapper mapper;

    public AddCustomerUseCaseImpl(CustomerRepository customerRepository, AddCustomerEntityMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public Void execute(AddCustomerDomain data) {
        // AddVehicleDomain -> VehicleEntity  Mapper
        CustomerEntity customerEntity = mapper.toEntity(data);
        customerRepository.create(customerEntity);
        return null;
    }
}
