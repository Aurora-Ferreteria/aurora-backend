package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.impl;

import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.impl.mapper.AddCustomerEmailMapper;
import co.edu.uco.aurora.infrastructure.externalservices.notification.WelcomeEmailSender;
import co.edu.uco.aurora.crosscutting.helper.TextHelper;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.AddCustomerUseCase;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.AddCustomerDomain;

// Importamos el puerto del catálogo
import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.ParameterCatalog;

import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.impl.mapper.AddCustomerEntityMapper;
import co.edu.uco.aurora.features.customer.rule.validator.ValidateCustomerEmailDoesNotExist;
import co.edu.uco.aurora.features.customer.rule.validator.ValidateCustomerIdentificationNumberDoesNotExist;
import co.edu.uco.aurora.features.customer.rule.validator.ValidateCustomerPhoneNumberDoesNotExist;
import co.edu.uco.aurora.features.identificationtype.rule.validator.ValidateIdentificationTypeExistsById;

import co.edu.uco.aurora.infrastructure.externalservices.notification.dto.WelcomeEmailDTO;
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
    private final AddCustomerEmailMapper emailMapper;
    private final WelcomeEmailSender emailSender;

    // 1. Declaramos el atributo que te faltaba
    private final ParameterCatalog parameterCatalog;

    // 2. Lo recibimos en el constructor para que Spring lo inyecte
    public AddCustomerUseCaseImpl(CustomerRepository customerRepository,
                                  IdentificationTypeRepository identificationTypeRepository,
                                  AddCustomerEntityMapper mapper, AddCustomerEmailMapper emailMapper,
                                  WelcomeEmailSender emailSender, ParameterCatalog parameterCatalog) {
        this.customerRepository = customerRepository;
        this.identificationTypeRepository = identificationTypeRepository;
        this.mapper = mapper;
        this.emailMapper = emailMapper;
        this.emailSender = emailSender;
        this.parameterCatalog = parameterCatalog; // 3. Lo asignamos
    }

    @Override
    public Void execute(AddCustomerDomain data) {

        // 1. Validar el formato del correo consultando nuestra caché dinámica (Redis/Strapi)
        String emailRegex = parameterCatalog.getParameterValue("EMAIL_REGEX");
        if (TextHelper.isEmpty(emailRegex)) {
            emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"; // Fallback seguro
        }

        // Ejecutamos la regla genérica pasándole el correo dinámico
        co.edu.uco.aurora.application.usecase.rule.generics.StringFormatValueIsValidRule.executeRule(
                data.getEmail(), "correo electrónico", emailRegex, true
        );

        // 2. Validaciones contra la base de datos MySQL
        ValidateIdentificationTypeExistsById.executeValidation(data.getIdentificationType(), identificationTypeRepository);
        ValidateCustomerIdentificationNumberDoesNotExist.executeValidation(data.getIdentificationNumber(), customerRepository);
        ValidateCustomerPhoneNumberDoesNotExist.executeValidation(data.getPhoneNumber(), customerRepository);
        ValidateCustomerEmailDoesNotExist.executeValidation(data.getEmail(), customerRepository);

        // 3. Creación y persistencia
        CustomerEntity customerEntity = mapper.toEntity(data);
        customerEntity.setId(UUID.randomUUID());
        customerRepository.create(customerEntity);

        // 4. Envío de Notificación
        try {
            WelcomeEmailDTO emailDto = emailMapper.toDto(data);
            emailSender.sendWelcomeEmail(emailDto);
        } catch (Exception e) {
            var technicalMessage = TextHelper.format(
                    MessagesEnum.WELCOME_EMAIL_SENDING_ERROR.name(),
                    data.getEmail(),
                    e.getMessage()
            );
            LOGGER.error(technicalMessage, e);
        }

        return null;
    }
}