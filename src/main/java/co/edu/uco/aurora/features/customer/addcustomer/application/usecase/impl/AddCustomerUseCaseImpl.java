package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.impl;

import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.helper.TextHelper;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.AddCustomerUseCase;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.AddCustomerDomain;

import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.impl.mapper.AddCustomerEntityMapper;
import co.edu.uco.aurora.features.customer.rule.validator.ValidateCustomerEmailDoesNotExist;
import co.edu.uco.aurora.features.customer.rule.validator.ValidateCustomerIdentificationNumberDoesNotExist;
import co.edu.uco.aurora.features.customer.rule.validator.ValidateCustomerPhoneNumberDoesNotExist;
import co.edu.uco.aurora.features.identificationtype.rule.validator.ValidateIdentificationTypeExistsById;

import co.edu.uco.aurora.infrastructure.persistence.repository.CustomerRepository;
import co.edu.uco.aurora.infrastructure.persistence.repository.IdentificationTypeRepository;
import co.edu.uco.aurora.infrastructure.persistence.repository.entity.CustomerEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddCustomerUseCaseImpl implements AddCustomerUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddCustomerUseCaseImpl.class);

    private final CustomerRepository customerRepository;
    private final IdentificationTypeRepository identificationTypeRepository;
    private final AddCustomerEntityMapper mapper;
    private final WelcomeEmailSender emailSender;

    public AddCustomerUseCaseImpl(CustomerRepository customerRepository,
                                  IdentificationTypeRepository identificationTypeRepository,
                                  AddCustomerEntityMapper mapper, WelcomeEmailSender emailSender) {
        this.customerRepository = customerRepository;
        this.identificationTypeRepository = identificationTypeRepository;
        this.mapper = mapper;
        this.emailSender = emailSender;
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

        try {
            emailSender.sendWelcomeEmail(data.getEmail(), data.getFullName());
        } catch (Exception e) {
            // Armamos el mensaje estructurado con tu catálogo
            var technicalMessage = TextHelper.format(
                    MessagesEnum.WELCOME_EMAIL_SENDING_ERROR.getContent(),
                    data.getEmail(),
                    e.getMessage()
            );
            LOGGER.error(technicalMessage, e);
        }

        return null;
    }
}