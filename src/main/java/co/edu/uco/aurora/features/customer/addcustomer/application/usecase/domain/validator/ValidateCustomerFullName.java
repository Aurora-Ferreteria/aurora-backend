package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.validator;

import co.edu.uco.aurora.application.usecase.rule.generics.StringFormatValueIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringLengthValuesIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringValuesIsPresentRule;

public final class ValidateCustomerFullName {

    private static final String NAME_REGEX = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,100}$";

    private static final String FIELD_NAME = "nombre completo";

    private ValidateCustomerFullName() {
    }

    public static String executeValidation(String fullName) {
        String sanitizedName = sanitize(fullName);
        StringValuesIsPresentRule.executeRule(sanitizedName, FIELD_NAME, true);
        StringLengthValuesIsValidRule.executeRule(sanitizedName, FIELD_NAME, 3, 100, true);
        StringFormatValueIsValidRule.executeRule(sanitizedName, FIELD_NAME, NAME_REGEX, true);

        return sanitizedName;
    }

    private static String sanitize(String data) {
        if (data != null) {
            return data.replaceAll("\\s+", " ").trim();
        }
        return null;
    }
}