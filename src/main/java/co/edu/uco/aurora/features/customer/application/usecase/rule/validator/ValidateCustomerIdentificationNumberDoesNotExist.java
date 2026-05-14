package co.edu.uco.aurora.features.customer.application.usecase.rule.validator;

import co.edu.uco.aurora.application.usecase.rule.validator.Validator;
import co.edu.uco.aurora.features.customer.application.usecase.rule.CustomerIdentificationNumberDoesNotExistRule;

public final class ValidateCustomerIdentificationNumberDoesNotExist implements Validator {

    private static final Validator instance = new ValidateCustomerIdentificationNumberDoesNotExist();

    private ValidateCustomerIdentificationNumberDoesNotExist() {
    }

    public static void executeValidation(final Object... data) {
        instance.validate(data);
    }

    @Override
    public void validate(Object... data) {
        CustomerIdentificationNumberDoesNotExistRule.executeRule(data);
    }
}