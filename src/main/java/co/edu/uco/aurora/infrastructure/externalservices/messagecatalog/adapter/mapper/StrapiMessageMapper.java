package co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.adapter.mapper;

import co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.dto.StrapiMessageResponseDTO;
import java.util.HashMap;
import java.util.Map;

public final class StrapiMessageMapper {

    private StrapiMessageMapper() {
    }

    public static Map<String, Map<String, String>> toI18nCacheMap(StrapiMessageResponseDTO response) {
        Map<String, Map<String, String>> i18nCache = new HashMap<>();

        if (response != null && response.getData() != null) {
            response.getData().forEach(item -> {
                // Leemos directamente de item, sin pasar por getAttributes()
                String key = item.getKey();
                String value = item.getValue();
                String locale = item.getLocale();

                if (key != null && value != null && locale != null) {
                    i18nCache.putIfAbsent(locale, new HashMap<>());
                    i18nCache.get(locale).put(key, value);
                }
            });
        }

        return i18nCache;
    }
}