package co.edu.uco.aurora.application.usecase.rule.generics;

import co.edu.uco.aurora.application.usecase.rule.Rule;
import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.helper.ObjectHelper;
import co.edu.uco.aurora.crosscutting.helper.TextHelper;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;

public final class StringValuesIsPresentRule implements Rule {

    private static final Rule instance = new StringValuesIsPresentRule();

    private StringValuesIsPresentRule() {
    }

    public static void executeRule(final Object... data) {
        instance.execute(data);
    }

    @Override
    public void execute(Object... data) {

        if (ObjectHelper.isNull(data)){
            var userMessage = MessagesEnum.STRING_VALUELS_PRESENT_RULE_DATA_IS_NULL.name();
            throw AuroraException.create(userMessage);
        }
        if (data.length < 3){
            var userMessage = MessagesEnum.STRING_VALUELS_PRESENT_RULE_DATA_LENGTH_INVALID.name();
            throw AuroraException.create(userMessage);
        }

        var stringData = (String) data[0];
        var dataName = (String) data[1];
        boolean mustApplyTrim = (boolean) data[2];

        if ((mustApplyTrim)
                ? TextHelper.isEmptyWithTrim(stringData) : TextHelper.isEmpty(stringData)) {
            var userMessage = TextHelper.format(
                    MessagesEnum.STRING_VALUELS_PRESENT_RULE_DATA_IS_EMPTY.name(),
                    dataName
            );
            throw AuroraException.create(userMessage);
        }

    }
}
