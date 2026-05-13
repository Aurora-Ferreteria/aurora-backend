package co.edu.uco.aurora.application.usecase.rule.generics;

import co.edu.uco.aurora.application.usecase.rule.Rule;
import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.helper.ObjectHelper;
import co.edu.uco.aurora.crosscutting.helper.TextHelper;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;

public class StringFormatValueIsValidRule implements Rule {

    private static final Rule instance = new StringFormatValueIsValidRule();

    private StringFormatValueIsValidRule() {

    }

    public static void executeRule(Object...data ) {
        instance.execute(data);
    }

    @Override
    public void execute(Object... data) {

        if (ObjectHelper.isNull(data)) {
            var userMessage = MessagesEnum.STRING_FORMAT_VALUES_IS_VALID_RULE_DATA_IS_NULL.getTitle();
            var technicalMessage = MessagesEnum.STRING_FORMAT_VALUES_IS_VALID_RULE_DATA_IS_NULL.getContent();
            throw AuroraException.create(userMessage, technicalMessage);
        }

        if (data.length < 4) {
            var userMessage = MessagesEnum.STRING_FORMAT_VALUES_IS_VALID_RULE_DATA_LENGTH_INVALID.getTitle();
            var technicalMessage = MessagesEnum.STRING_FORMAT_VALUES_IS_VALID_RULE_DATA_LENGTH_INVALID.getContent();
            throw AuroraException.create(userMessage, technicalMessage);
        }

        var stringData = (String) data[0];
        var dataName = (String) data[1];
        var regex = (String) data[2];
        boolean mustApplyTrim = (boolean) data[3];

        var dataToValidate = (mustApplyTrim)
                ? TextHelper.getDefaultWithTrim(stringData)
                : stringData;

        if (!TextHelper.isEmpty(dataToValidate) && !TextHelper.matchesRegex(dataToValidate, regex)) {
            var userMessage = TextHelper.format(
                    MessagesEnum.STRING_FORMAT_VALUES_IS_VALID_RULE_FORMAT_IS_INVALID.getTitle(),
                    dataName
            );
            var technicalMessage = TextHelper.format(
                    MessagesEnum.STRING_FORMAT_VALUES_IS_VALID_RULE_FORMAT_IS_INVALID.getContent(),
                    dataName, regex
            );
            throw AuroraException.create(userMessage, technicalMessage);
        }
    }
}