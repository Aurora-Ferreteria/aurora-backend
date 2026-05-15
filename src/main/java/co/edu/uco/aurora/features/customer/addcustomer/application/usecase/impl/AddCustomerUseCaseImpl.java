package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.impl;

import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.AddCustomerUseCase;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.AddCustomerDomain;

import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.impl.mapper.AddCustomerEntityMapper;
import co.edu.uco.aurora.features.customer.application.usecase.rule.validator.ValidateCustomerEmailDoesNotExist;
import co.edu.uco.aurora.features.customer.application.usecase.rule.validator.ValidateCustomerIdentificationNumberDoesNotExist;
import co.edu.uco.aurora.features.customer.application.usecase.rule.validator.ValidateCustomerPhoneNumberDoesNotExist;
import co.edu.uco.aurora.features.identificationtype.application.usecase.rule.validator.ValidateIdentificationTypeExistsById;

import co.edu.uco.aurora.infrastructure.persistence.repository.CustomerRepository;
import co.edu.uco.aurora.infrastructure.persistence.repository.IdentificationTypeRepository;
import co.edu.uco.aurora.infrastructure.persistence.repository.entity.CustomerEntity;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddCustomerUseCaseImpl implements AddCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final IdentificationTypeRepository identificationTypeRepository;
    private final AddCustomerEntityMapper mapper;

    public AddCustomerUseCaseImpl(CustomerRepository customerRepository,
                                  IdentificationTypeRepository identificationTypeRepository,
                                  AddCustomerEntityMapper mapper) {
        this.customerRepository = customerRepository;
        this.identificationTypeRepository = identificationTypeRepository;
        this.mapper = mapper;
    }

    @Override
    public Void execute(AddCustomerDomain data) {

        ValidateIdentificationTypeExistsById.executeValidation(data.getIdentificationType(), identificationTypeRepository);
        ValidateCustomerIdentificationNumberDoesNotExist.executeValidation(data.getIdentificationNumber(), customerRepository);
        ValidateCustomerPhoneNumberDoesNotExist.executeValidation(data.getPhoneNumber(), customerRepository);
        ValidateCustomerEmailDoesNotExist.executeValidation(data.getEmail(), customerRepository);

        CustomerEntity customerEntity = mapper.toEntity(data);

        customerEntity.setId(UUID.randomUUID());

        customerRepository.create(customerEntity);

        return null;
    }
}