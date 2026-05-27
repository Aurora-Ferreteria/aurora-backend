package co.edu.uco.aurora.crosscutting.helper;

import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.ParameterCatalog;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TextHelperInitializer {

    private final ParameterCatalog parameterCatalog;

    public TextHelperInitializer(ParameterCatalog parameterCatalog) {
        this.parameterCatalog = parameterCatalog;
    }

    @PostConstruct
    public void initVariables() {
        TextHelper.EMAIL_REGEX = parameterCatalog.getParameterValue("EMAIL_REGEX");
        TextHelper.PHONE_REGEX = parameterCatalog.getParameterValue("PHONE_REGEX");
        TextHelper.NAME_REGEX = parameterCatalog.getParameterValue("NAME_REGEX");
        TextHelper.ID_NUMBER_REGEX = parameterCatalog.getParameterValue("ID_NUMBER_REGEX");
    }
}