package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.validator;

import co.edu.uco.aurora.application.usecase.rule.generics.StringFormatValueIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringLengthValuesIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringValuesIsPresentRule;
import co.edu.uco.aurora.crosscutting.helper.TextHelper;

public final class ValidateCustomerFullName {

    private static final String FIELD_NAME = "nombre completo";

    private ValidateCustomerFullName() {
    }

    public static String executeValidation(String fullName) {
        String sanitizedName = TextHelper.getDefaultWithTrim(fullName).replaceAll("\\s+", " ");

        StringValuesIsPresentRule.executeRule(sanitizedName, FIELD_NAME, true);
        StringLengthValuesIsValidRule.executeRule(sanitizedName, FIELD_NAME, 3, 100, true);

        StringFormatValueIsValidRule.executeRule(sanitizedName, FIELD_NAME, TextHelper.getNameRegex(), true);

        return sanitizedName;
    }
}