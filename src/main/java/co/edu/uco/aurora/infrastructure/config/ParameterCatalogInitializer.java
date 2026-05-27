package co.edu.uco.aurora.infrastructure.config;

import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.ParameterCatalog;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ParameterCatalogInitializer implements CommandLineRunner {

    private final ParameterCatalog parameterCatalog;

    public ParameterCatalogInitializer(ParameterCatalog parameterCatalog) {
        this.parameterCatalog = parameterCatalog;
    }

    @Override
    public void run(String... args) throws Exception {
        parameterCatalog.loadParameters();
    }
}