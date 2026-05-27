package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.validator;

import co.edu.uco.aurora.application.usecase.rule.generics.StringFormatValueIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringLengthValuesIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringValuesIsPresentRule;
import co.edu.uco.aurora.crosscutting.helper.TextHelper;

public final class ValidateCustomerPhoneNumber {

    private static final String FIELD_NAME = "número de teléfono";

    private ValidateCustomerPhoneNumber() {
    }

    public static String executeValidation(String phoneNumber) {
        String sanitizedPhone = TextHelper.getDefaultWithTrim(phoneNumber).replaceAll("\\s+", "");

        StringValuesIsPresentRule.executeRule(sanitizedPhone, FIELD_NAME, true);
        StringLengthValuesIsValidRule.executeRule(sanitizedPhone, FIELD_NAME, 7, 20, true);

        StringFormatValueIsValidRule.executeRule(sanitizedPhone, FIELD_NAME, TextHelper.getPhoneRegex(), true);

        return sanitizedPhone;
    }
}