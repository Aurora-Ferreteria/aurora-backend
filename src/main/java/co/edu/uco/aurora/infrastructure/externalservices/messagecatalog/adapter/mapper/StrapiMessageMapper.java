package co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.adapter.mapper;

import co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.dto.StrapiMessageResponseDTO;
import java.util.HashMap;
import java.util.Map;

public final class StrapiMessageMapper {

    private StrapiMessageMapper() {
        // Constructor privado para evitar instanciar clases utilitarias
    }

    // Traduce el DTO complejo de Strapi a un Mapa simple de Java
    public static Map<String, String> toCacheMap(StrapiMessageResponseDTO response) {
        Map<String, String> cleanedMessages = new HashMap<>();

        if (response != null && response.getData() != null) {
            response.getData().forEach(item -> {
                if (item.getAttributes() != null) {
                    String key = item.getAttributes().getKey();
                    String value = item.getAttributes().getValue();
                    cleanedMessages.put(key, value);
                }
            });
        }

        return cleanedMessages;
    }
}