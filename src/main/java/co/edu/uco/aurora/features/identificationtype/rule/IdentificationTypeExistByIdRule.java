package co.edu.uco.aurora.features.identificationtype.rule;

import co.edu.uco.aurora.application.usecase.rule.Rule;
import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.helper.ObjectHelper;
import co.edu.uco.aurora.crosscutting.helper.TextHelper;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.infrastructure.persistence.repository.IdentificationTypeRepository;

import java.util.UUID;

public final class IdentificationTypeExistByIdRule implements Rule {

    private static final Rule instance = new IdentificationTypeExistByIdRule();

    private IdentificationTypeExistByIdRule() {

    }

    public static void executeRule(final Object... data) {
        instance.execute(data);
    }

    @Override
    public void execute(Object... data) {

        if (ObjectHelper.isNull(data)){
            var userMessage = MessagesEnum.ID_TYPE_EXISTS_BY_ID_RULE_DATA_IS_NULL.getTitle();
            var technicalMessage = MessagesEnum.ID_TYPE_EXISTS_BY_ID_RULE_DATA_IS_NULL.getContent();
            throw AuroraException.create(userMessage, technicalMessage);
        }
        if (data.length < 2){
            var userMessage = MessagesEnum.ID_TYPE_EXISTS_BY_ID_RULE_DATA_LENGTH_INVALID.getTitle();
            var technicalMessage = MessagesEnum.ID_TYPE_EXISTS_BY_ID_RULE_DATA_LENGTH_INVALID.getContent();
            throw AuroraException.create(userMessage, technicalMessage);
        }

        var id = (UUID) data[0];
        var repository = (IdentificationTypeRepository) data[1];

        if (!repository.existsById(id)) {
            var userMessage = MessagesEnum.ID_TYPE_EXISTS_BY_ID_RULE_ID_TYPE_NOT_FOUND.getTitle();
            var technicalMessage = TextHelper.format(
                    MessagesEnum.ID_TYPE_EXISTS_BY_ID_RULE_ID_TYPE_NOT_FOUND.getContent(),
                    id.toString()
            );
            throw AuroraException.create(userMessage, technicalMessage);
        }
    }
 }

