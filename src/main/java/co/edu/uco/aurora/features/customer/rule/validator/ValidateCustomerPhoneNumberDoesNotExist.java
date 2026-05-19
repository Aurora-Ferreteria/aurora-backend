package co.edu.uco.aurora.features.customer.rule.validator;

import co.edu.uco.aurora.application.usecase.rule.validator.Validator;
import co.edu.uco.aurora.features.customer.rule.CustomerPhoneNumberDoesNotExistRule;

public final class ValidateCustomerPhoneNumberDoesNotExist implements Validator {

    private static final Validator instance = new ValidateCustomerPhoneNumberDoesNotExist();

    private ValidateCustomerPhoneNumberDoesNotExist() {
    }

    public static void executeValidation(final Object... data) {
        instance.validate(data);
    }

    @Override
    public void validate(Object... data) {
        CustomerPhoneNumberDoesNotExistRule.executeRule(data);
    }
}