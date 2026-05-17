package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.validator;

import co.edu.uco.aurora.application.usecase.rule.generics.StringFormatValueIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringLengthValuesIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringValuesIsPresentRule;
import co.edu.uco.aurora.crosscutting.helper.TextHelper;

public final class ValidateCustomerEmail {

    private static final String FIELD_NAME = "correo electrónico";

    private ValidateCustomerEmail() {
    }

    public static String executeValidation(String email) {
        String sanitizedEmail = TextHelper.getDefaultWithTrim(email).replaceAll("\\s+", "");

        StringValuesIsPresentRule.executeRule(sanitizedEmail, FIELD_NAME, true);
        StringLengthValuesIsValidRule.executeRule(sanitizedEmail, FIELD_NAME, 5, 150, true);

        StringFormatValueIsValidRule.executeRule(sanitizedEmail, FIELD_NAME, TextHelper.EMAIL_REGEX, true);

        return sanitizedEmail;
    }
}