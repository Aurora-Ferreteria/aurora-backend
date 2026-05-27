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

        System.out.println("🔄 [Redis-Cache] Caché de mensajes vacía. Consultando a Strapi...");
        String url = strapiUrl + "?populate=*&publicationState=preview&pagination[pageSize]=100";
        StrapiMessageResponseDTO response = restTemplate.getForObject(url, StrapiMessageResponseDTO.class);
        Map<String, Map<String, String>> messagesMap = StrapiMessageMapper.toI18nCacheMap(response);

        if (redisCache != null && messagesMap != null) {
            redisCache.put("allMessages", messagesMap);
            System.out.println("✅ Mensajes guardados en Redis con éxito.");
        }

        return messagesMap;
    }

    public void clearCache() {
        Cache redisCache = cacheManager.getCache("messageCatalog");
        if (redisCache != null) {
            redisCache.evict("allMessages");
            System.out.println("🗑️ [Redis-Cache] Caché de mensajes limpiada.");
        }
    }

    @Override
    public String getMessageContent(MessagesEnum message) {
        Map<String, Map<String, String>> messageCache = loadAllMessagesFromStrapi();

        if (messageCache == null || messageCache.isEmpty()) {
            return message.name();
        }

        String currentLanguage = LocaleContextHolder.getLocale().getLanguage();
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