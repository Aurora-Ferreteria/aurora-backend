package co.edu.uco.aurora.application.usecase.rule.customer;

import co.edu.uco.aurora.application.usecase.rule.Rule;
import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.helper.ObjectHelper;
import co.edu.uco.aurora.crosscutting.helper.TextHelper;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.infrastructure.persistence.repository.CustomerRepository;

public class CustomerIdentificationNumberDoesNotExistRule implements Rule {

    private static final Rule instance = new CustomerIdentificationNumberDoesNotExistRule();

    private CustomerIdentificationNumberDoesNotExistRule() {
    }

    public static void executeRule(final Object... data) {
        instance.execute(data);
    }

    @Override
    public void execute(Object... data) {

        if (ObjectHelper.isNull(data)) {
            var userMessage = MessagesEnum.CUSTOMER_ID_NUMBER_DOES_NOT_EXIST_RULE_DATA_IS_NULL.getTitle();
            var technicalMessage = MessagesEnum.CUSTOMER_ID_NUMBER_DOES_NOT_EXIST_RULE_DATA_IS_NULL.getContent();
            throw AuroraException.create(userMessage, technicalMessage);
        }

        if (data.length < 2) {
            var userMessage = MessagesEnum.CUSTOMER_ID_NUMBER_DOES_NOT_EXIST_RULE_DATA_LENGTH_INVALID.getTitle();
            var technicalMessage = MessagesEnum.CUSTOMER_ID_NUMBER_DOES_NOT_EXIST_RULE_DATA_LENGTH_INVALID.getContent();
            throw AuroraException.create(userMessage, technicalMessage);
        }

        var idNumber = (String) data[0];
        var repository = (CustomerRepository) data[1];

        boolean exists = repository.existsByIdentificationNumber(idNumber);

        if (exists) {
            var userMessage = MessagesEnum.CUSTOMER_ID_NUMBER_DOES_NOT_EXIST_RULE_CUSTOMER_ALREADY_EXISTS.getTitle();
            var technicalMessage = TextHelper.format(
                    MessagesEnum.CUSTOMER_ID_NUMBER_DOES_NOT_EXIST_RULE_CUSTOMER_ALREADY_EXISTS.getContent(),
                    idNumber
            );
            throw AuroraException.create(userMessage, technicalMessage);
        }

    }
}
