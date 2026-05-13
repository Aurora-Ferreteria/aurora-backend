package co.edu.uco.aurora.application.usecase.rule.generics;

import co.edu.uco.aurora.application.usecase.rule.Rule;
import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.helper.ObjectHelper;
import co.edu.uco.aurora.crosscutting.helper.TextHelper;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;

public final class StringLengthValuesIsValidRule implements Rule {

    private static final Rule instance = new StringLengthValuesIsValidRule();

    private StringLengthValuesIsValidRule() {

    }

    public static void executeRule(final Object... data) {
        instance.execute(data);
    }

    @Override
    public void execute(Object... data) {

        if (ObjectHelper.isNull(data)){
            var userMessage = MessagesEnum.STRING_LENGTH_VALUES_IS_VALID_RULE_DATA_IS_NULL.getTitle();
            var technicalMessage = MessagesEnum.STRING_LENGTH_VALUES_IS_VALID_RULE_DATA_IS_NULL.getContent();
            throw AuroraException.create(userMessage, technicalMessage);
        }
        if (data.length < 5){
            var userMessage = MessagesEnum.STRING_LENGTH_VALUES_IS_VALID_RULE_DATA_LENGTH_INVALID.getTitle();
            var technicalMessage = MessagesEnum.STRING_LENGTH_VALUES_IS_VALID_RULE_DATA_LENGTH_INVALID.getContent();
            throw AuroraException.create(userMessage, technicalMessage);
        }

        var stringData = (String) data[0];
        var dataName = (String) data[1];

        int minLength = (int) data[2];
        int maxLength = (int) data[3];
        boolean mustApplyTrim = (boolean) data[4];

        if (!TextHelper.lengthIsValid(stringData, minLength, maxLength, mustApplyTrim)) {

            var minLengthStr = String.valueOf(minLength);
            var maxLengthStr = String.valueOf(maxLength);

            var userMessage = TextHelper.format(
                    MessagesEnum.STRING_LENGTH_VALUES_IS_VALID_RULE_LENGTH_IS_INVALID.getTitle(),
                    dataName, minLengthStr, maxLengthStr
            );
            var technicalMessage = TextHelper.format(
                    MessagesEnum.STRING_LENGTH_VALUES_IS_VALID_RULE_LENGTH_IS_INVALID.getContent(),
                    dataName, minLengthStr, maxLengthStr
            );
            throw AuroraException.create(userMessage, technicalMessage);
        }

    }
}