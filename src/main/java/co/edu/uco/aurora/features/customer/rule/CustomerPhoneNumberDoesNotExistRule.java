package co.edu.uco.aurora.features.customer.rule;

import co.edu.uco.aurora.application.usecase.rule.Rule;
import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.helper.ObjectHelper;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.infrastructure.persistence.repository.CustomerRepository;

public final class CustomerPhoneNumberDoesNotExistRule implements Rule {

    private static final Rule instance = new CustomerPhoneNumberDoesNotExistRule();

    private CustomerPhoneNumberDoesNotExistRule() {
    }

    public static void executeRule(final Object... data) {
        instance.execute(data);
    }

    @Override
    public void execute(Object... data) {

        if (ObjectHelper.isNull(data)) {
            var userMessage = MessagesEnum.CUSTOMER_PHONE_NUMBER_DOES_NOT_EXIST_RULE_DATA_IS_NULL.name();
            throw AuroraException.create(userMessage);
        }

        if (data.length < 2) {
            var userMessage = MessagesEnum.CUSTOMER_PHONE_NUMBER_DOES_NOT_EXIST_RULE_DATA_LENGTH_INVALID.name();
            throw AuroraException.create(userMessage);
        }

        var phoneNumber = (String) data[0];
        var repository = (CustomerRepository) data[1];
        boolean exists = repository.existsByPhoneNumber(phoneNumber);

        if (exists) {
            var userMessage = MessagesEnum.CUSTOMER_PHONE_NUMBER_DOES_NOT_EXIST_RULE_CUSTOMER_ALREADY_EXISTS.name();
            throw AuroraException.create(userMessage);
        }
    }
}