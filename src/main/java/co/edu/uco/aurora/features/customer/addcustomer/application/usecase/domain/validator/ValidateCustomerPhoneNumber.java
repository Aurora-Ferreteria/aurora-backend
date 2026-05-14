package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.validator;

import co.edu.uco.aurora.application.usecase.rule.generics.StringFormatValueIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringLengthValuesIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringValuesIsPresentRule;

public final class ValidateCustomerPhoneNumber {

    private static final String PHONE_NUMBER_REGEX = "^\\+?\\d{7,20}$";

    private static final String FIELD_NAME = "número de teléfono";

    private ValidateCustomerPhoneNumber() {
    }

    public static String executeValidation(String phoneNumber) {
        String sanitizedPhone = sanitize(phoneNumber);
        StringValuesIsPresentRule.executeRule(sanitizedPhone, FIELD_NAME, true);
        StringLengthValuesIsValidRule.executeRule(sanitizedPhone, FIELD_NAME, 7, 20, true);
        StringFormatValueIsValidRule.executeRule(sanitizedPhone, FIELD_NAME, PHONE_NUMBER_REGEX, true);

        return sanitizedPhone;
    }

    private static String sanitize(String data) {
        if (data != null) {
            return data.replaceAll("\\s+", "").trim();
        }
        return null;
    }
}