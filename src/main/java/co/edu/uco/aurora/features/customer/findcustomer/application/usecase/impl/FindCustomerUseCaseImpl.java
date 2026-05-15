package co.edu.uco.aurora.features.customer.findcustomer.application.usecase.impl;

import co.edu.uco.aurora.features.customer.findcustomer.application.usecase.FindCustomerUseCase;
import co.edu.uco.aurora.features.customer.findcustomer.application.usecase.domain.FindCustomerDomain;
import co.edu.uco.aurora.features.customer.findcustomer.application.usecase.impl.mapper.FindCustomerEntityMapper;
import co.edu.uco.aurora.infrastructure.persistence.repository.CustomerRepository;
import co.edu.uco.aurora.infrastructure.persistence.repository.entity.CustomerEntity;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindCustomerUseCaseImpl implements FindCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final FindCustomerEntityMapper mapper;

    public FindCustomerUseCaseImpl(CustomerRepository customerRepository, FindCustomerEntityMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public List<FindCustomerDomain> execute(Void data) {
        List<CustomerEntity> entities = customerRepository.findAll();
        return mapper.toDomains(entities);
    }
}