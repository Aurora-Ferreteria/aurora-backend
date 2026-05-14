package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.validator;

import co.edu.uco.aurora.application.usecase.rule.generics.StringFormatValueIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringLengthValuesIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringValuesIsPresentRule;

public final class ValidateCustomerEmail {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static final String FIELD_NAME = "correo electrónico";

    private ValidateCustomerEmail() {
    }

    public static String executeValidation(String email) {
        String sanitizedEmail = sanitize(email);
        StringValuesIsPresentRule.executeRule(sanitizedEmail, FIELD_NAME, true);
        StringLengthValuesIsValidRule.executeRule(sanitizedEmail, FIELD_NAME, 5, 150, true);
        StringFormatValueIsValidRule.executeRule(sanitizedEmail, FIELD_NAME, EMAIL_REGEX, true);

        return sanitizedEmail;
    }

    private static String sanitize(String data) {
        if (data != null) {
            return data.replaceAll("\\s+", "").trim();
        }
        return null;
    }
}