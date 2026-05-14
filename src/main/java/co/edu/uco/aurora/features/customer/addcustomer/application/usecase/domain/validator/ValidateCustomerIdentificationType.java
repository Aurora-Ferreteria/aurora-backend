package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.validator;

import co.edu.uco.aurora.application.usecase.rule.generics.IdValueIsNotDefaultValueRule;
import java.util.UUID;

public final class ValidateCustomerIdentificationType {

    private ValidateCustomerIdentificationType() {
    }

    public static UUID executeValidation(UUID identificationType) {

        IdValueIsNotDefaultValueRule.executeRule(identificationType, "tipo de identificación");

        return identificationType;
    }
}