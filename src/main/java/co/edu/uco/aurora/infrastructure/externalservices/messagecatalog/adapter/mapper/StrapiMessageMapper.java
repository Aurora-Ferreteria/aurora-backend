package co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.adapter.mapper;

import co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.dto.StrapiMessageResponseDTO;
import java.util.HashMap;
import java.util.Map;

public final class StrapiMessageMapper {

    private StrapiMessageMapper() {
    }

    public static Map<String, Map<String, String>> toI18nCacheMap(StrapiMessageResponseDTO response) {
        // 🚀 El mapa principal que guardará los idiomas (Ej: "es" -> {...}, "en" -> {...})
        Map<String, Map<String, String>> i18nCache = new HashMap<>();

        if (response != null && response.getData() != null) {
            response.getData().forEach(item -> {
                if (item.getAttributes() != null) {
                    String key = item.getAttributes().getKey();
                    String value = item.getAttributes().getValue();

                    // 🚀 Leemos el idioma directamente de los atributos de Strapi
                    String locale = item.getAttributes().getLocale();

                    // Verificamos que ninguno de los datos clave venga nulo
                    if (key != null && value != null && locale != null) {

                        // Si es la primera vez que vemos este idioma, creamos su diccionario interno
                        i18nCache.putIfAbsent(locale, new HashMap<>());

                        // Entramos al diccionario de ese idioma y guardamos la clave y el valor
                        i18nCache.get(locale).put(key, value);
                    }
                }
            });
        }

        return i18nCache;
    }
}