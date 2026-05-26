package co.edu.uco.aurora.infrastructure.externalservices.parametercatalog;

public interface ParameterCatalog {

    // Método para forzar la carga desde Strapi a Redis
    void loadParameters();

    // Método que usará tu sistema para pedir la expresión regular pasándole el KEY (ej: "EMAIL_REGEX")
    String getParameterValue(String key);
}