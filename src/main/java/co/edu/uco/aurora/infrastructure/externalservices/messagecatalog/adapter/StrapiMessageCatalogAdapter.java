package co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.adapter;

import co.edu.uco.aurora.crosscutting.messagescatalog.MessageCatalogService;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.dto.StrapiMessageResponseDTO;
import co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.adapter.mapper.StrapiMessageMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StrapiMessageCatalogAdapter implements MessageCatalogService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final CacheManager cacheManager;

    @Value("${api.strapi.url}")
    private String strapiUrl;

    public StrapiMessageCatalogAdapter(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Map<String, String>> loadAllMessagesFromStrapi() {
        Cache redisCache = cacheManager.getCache("messageCatalog");

        if (redisCache != null) {
            Cache.ValueWrapper wrapper = redisCache.get("allMessages");
            if (wrapper != null) {
                return (Map<String, Map<String, String>>) wrapper.get();
            }
        }

        System.out.println("🔄 [Redis-Cache] Caché de mensajes vacía. Consultando a Strapi por locale...");

        Map<String, Map<String, String>> finalMessagesMap = new HashMap<>();

        try {
            for (String locale : List.of("es", "en")) {
                String url = strapiUrl + "?pagination[pageSize]=100&locale=" + locale;
                StrapiMessageResponseDTO response = restTemplate.getForObject(url, StrapiMessageResponseDTO.class);

                Map<String, Map<String, String>> partialMap = StrapiMessageMapper.toI18nCacheMap(response);

                if (partialMap != null && !partialMap.isEmpty()) {
                    finalMessagesMap.putAll(partialMap);
                    System.out.println("✅ Mensajes de sistema [" + locale + "] cargados.");
                }
            }

            if (redisCache != null && !finalMessagesMap.isEmpty()) {
                redisCache.put("allMessages", finalMessagesMap);
                System.out.println("✅ Todos los mensajes guardados en Redis con éxito. Locales: " + finalMessagesMap.keySet());
            }

        } catch (Exception e) {
            System.err.println("❌ Error cargando mensajes de Strapi: " + e.getMessage());
        }

        return finalMessagesMap;
    }

    public void clearCache() {
        Cache redisCache = cacheManager.getCache("messageCatalog");
        if (redisCache != null) {
            redisCache.evict("allMessages");
            System.out.println("🗑️ [Redis-Cache] Caché de mensajes limpiada forzosamente.");
        }
    }

    @Override
    public String getMessageContent(MessagesEnum message) {
        String currentLanguage = LocaleContextHolder.getLocale().getLanguage();
        Map<String, Map<String, String>> messageCache = loadAllMessagesFromStrapi();

        // 🛠️ TU NUEVA LÓGICA: Si el caché tiene datos, pero NO contiene el idioma que pide el Header...
        if (messageCache != null && !messageCache.isEmpty() && !messageCache.containsKey(currentLanguage)) {
            System.out.println("⚠️ [Redis-Cache] El idioma '" + currentLanguage + "' no existe en el caché actual. ¡Datos desactualizados detectados!");
            clearCache(); // Vaciamos el "allMessages" viejo de Redis
            messageCache = loadAllMessagesFromStrapi(); // Forzamos la recarga bilingüe desde Strapi
        }

        if (messageCache == null || messageCache.isEmpty()) {
            return message.name();
        }

        Map<String, String> localeMap = messageCache.get(currentLanguage);

        if (localeMap == null) {
            String matchingKey = messageCache.keySet().stream()
                    .filter(key -> key.startsWith(currentLanguage))
                    .findFirst()
                    .orElse(null);
            if (matchingKey != null) {
                localeMap = messageCache.get(matchingKey);
            }
        }

        if (localeMap == null && !messageCache.isEmpty()) {
            localeMap = messageCache.values().iterator().next();
        }

        return (localeMap != null) ? localeMap.getOrDefault(message.name(), message.name()) : message.name();
    }

    @Override
    public String getMessageContent(MessagesEnum message, String... params) {
        String pattern = getMessageContent(message);
        try {
            return MessageFormat.format(pattern, (Object[]) params);
        } catch (IllegalArgumentException e) {
            return pattern;
        }
    }
}