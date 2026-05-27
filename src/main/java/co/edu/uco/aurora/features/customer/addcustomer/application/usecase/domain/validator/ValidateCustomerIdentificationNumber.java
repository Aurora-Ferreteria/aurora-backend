package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.validator;

import co.edu.uco.aurora.application.usecase.rule.generics.StringFormatValueIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringLengthValuesIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringValuesIsPresentRule;
import co.edu.uco.aurora.crosscutting.helper.TextHelper;

public final class ValidateCustomerIdentificationNumber {

    private static final String FIELD_NAME = "número de identificación";

    private ValidateCustomerIdentificationNumber() {
    }

    public static String executeValidation(String identificationNumber) {
        String sanitizedIdNumber = TextHelper.getDefaultWithTrim(identificationNumber).replaceAll("\\s+", "");

        StringValuesIsPresentRule.executeRule(sanitizedIdNumber, FIELD_NAME, true);
        StringLengthValuesIsValidRule.executeRule(sanitizedIdNumber, FIELD_NAME, 6, 25, true);

        StringFormatValueIsValidRule.executeRule(sanitizedIdNumber, FIELD_NAME, TextHelper.getIdNumberRegex(), true);

        return sanitizedIdNumber;
    }
}