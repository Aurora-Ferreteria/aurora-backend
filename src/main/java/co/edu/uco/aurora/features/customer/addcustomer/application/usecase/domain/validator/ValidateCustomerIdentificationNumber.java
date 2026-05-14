package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.validator;

import co.edu.uco.aurora.application.usecase.rule.generics.StringFormatValueIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringLengthValuesIsValidRule;
import co.edu.uco.aurora.application.usecase.rule.generics.StringValuesIsPresentRule;

public final class ValidateCustomerIdentificationNumber {

    private static final String ID_NUMBER_REGEX = "^[0-9]+$";

    private static final String FIELD_NAME = "número de identificación";

    private ValidateCustomerIdentificationNumber() {
    }

    public static String executeValidation(String identificationNumber) {
        String sanitizedIdNumber = sanitize(identificationNumber);
        StringValuesIsPresentRule.executeRule(sanitizedIdNumber, FIELD_NAME, true);
        StringLengthValuesIsValidRule.executeRule(sanitizedIdNumber, FIELD_NAME, 6, 25, true);
        StringFormatValueIsValidRule.executeRule(sanitizedIdNumber, FIELD_NAME, ID_NUMBER_REGEX, true);

        return sanitizedIdNumber;
    }

    private static String sanitize(String data) {
        if (data != null) {
            return data.replaceAll("\\s+", "").trim();
        }
        return null;
    }
}