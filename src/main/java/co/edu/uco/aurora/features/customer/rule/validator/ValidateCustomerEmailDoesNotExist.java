package co.edu.uco.aurora.features.customer.rule.validator;

import co.edu.uco.aurora.application.usecase.rule.validator.Validator;
import co.edu.uco.aurora.features.customer.rule.CustomerEmailDoesNotExistRule;

public final class ValidateCustomerEmailDoesNotExist implements Validator {

    private static final Validator instance = new ValidateCustomerEmailDoesNotExist();

    private ValidateCustomerEmailDoesNotExist() {
    }

    public static void executeValidation(final Object... data) {
        instance.validate(data);
    }

    @Override
    public void validate(Object... data) {
        CustomerEmailDoesNotExistRule.executeRule(data);
    }
}