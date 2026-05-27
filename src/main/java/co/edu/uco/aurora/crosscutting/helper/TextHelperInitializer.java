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
    public void init() {
        // Solo le pasamos la referencia al catálogo.
        // TextHelper ya no guarda el String; lo pedirá dinámicamente.
        TextHelper.setParameterCatalog(parameterCatalog);
    }
}