package co.edu.uco.aurora.features.identificationtype.application.usecase.rule.validator;

import co.edu.uco.aurora.features.identificationtype.application.usecase.rule.IdentificationTypeExistByIdRule;
import co.edu.uco.aurora.application.usecase.rule.validator.Validator;

public class ValidateIdentificationTypeExistsById implements Validator {

    private static final Validator instance = new ValidateIdentificationTypeExistsById();

    private ValidateIdentificationTypeExistsById() {

    }

    public static void executeValidation(final Object... data) {
        instance.validate(data);
    }

    @Override
    public void validate(Object... data) {
        IdentificationTypeExistByIdRule.executeRule(data);
    }
}
