package co.edu.uco.aurora.application.usecase.rule.generics;

import co.edu.uco.aurora.application.usecase.rule.Rule;
import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.helper.ObjectHelper;
import co.edu.uco.aurora.crosscutting.helper.UUIDHelper;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;

import java.util.UUID;

public final class IdValueIsNotDefaultValueRule implements Rule {

    private static final Rule instance = new IdValueIsNotDefaultValueRule();

    private IdValueIsNotDefaultValueRule() {

    }

    public static void executeRule(final Object... data) {
        instance.execute(data);
    }

    @Override
    public void execute(Object... data) {

        if (ObjectHelper.isNull(data)) {
            throw AuroraException.create(
                    MessagesEnum.ID_VALUE_IS_NOT_DEFAULT_RULE_DATA_IS_NULL.getMessage()
            );
        }

        if (data.length < 2) {
            throw AuroraException.create(
                    MessagesEnum.ID_VALUE_IS_NOT_DEFAULT_RULE_DATA_LENGTH_INVALID.getMessage()
            );
        }

        var uuid = (UUID) data[0];
        var dataName = (String) data[1];

        if (ObjectHelper.isNull(uuid)) {
            throw AuroraException.create(
                    MessagesEnum.ID_VALUE_IS_NOT_DEFAULT_RULE_UUID_IS_NULL.getMessage(dataName)
            );
        }

        if (UUIDHelper.isDefaultUUID(uuid)) {
            throw AuroraException.create(
                    MessagesEnum.ID_VALUE_IS_NOT_DEFAULT_RULE_UUID_IS_DEFAULT.getMessage(dataName)
            );
        }
    }
}