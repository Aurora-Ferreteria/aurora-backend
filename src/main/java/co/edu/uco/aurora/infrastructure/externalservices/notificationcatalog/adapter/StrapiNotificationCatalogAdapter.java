package co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.adapter;

import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.NotificationCatalog;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.adapter.mapper.StrapiNotificationMapper;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto.NotificationCatalogDTO;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto.StrapiNotificationResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class StrapiNotificationCatalogAdapter implements NotificationCatalog {

    private static final Logger log = LoggerFactory.getLogger(StrapiNotificationCatalogAdapter.class);

    private static final String CACHE_NAME = "notificationsCache";
    private static final String CACHE_KEY = "allNotifications";

    @Value("${api.strapi.notifications.url}")
    private String strapiNotificationsUrl;

    private final RestTemplate restTemplate;
    private final StrapiNotificationMapper mapper;
    private final CacheManager cacheManager;

    public StrapiNotificationCatalogAdapter(RestTemplate restTemplate,
                                            StrapiNotificationMapper mapper,
                                            CacheManager cacheManager) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.cacheManager = cacheManager;
    }

    @Override
    public void loadCatalog() {
        try {
            log.info("🔄 [Redis-Cache] Cargando catálogo de notificaciones desde Strapi...");

            Map<String, Map<String, NotificationCatalogDTO>> byLocale = new HashMap<>();

            for (String locale : List.of("es", "en")) {
                String url = strapiNotificationsUrl + "&locale=" + locale;
                StrapiNotificationResponseDTO response = restTemplate.getForObject(url, StrapiNotificationResponseDTO.class);

                if (response != null && response.getData() != null) {
                    Map<String, NotificationCatalogDTO> localeMap = new HashMap<>();
                    response.getData().forEach(data -> {
                        NotificationCatalogDTO dto = mapper.toCatalogDto(data);
                        localeMap.put(dto.getKey(), dto);
                    });
                    byLocale.put(locale, localeMap);
                    log.info("✅ Notificaciones [{}] cargadas: {}", locale, localeMap.size());
                }
            }

            Cache redisCache = cacheManager.getCache(CACHE_NAME);
            if (redisCache != null) {
                redisCache.put(CACHE_KEY, byLocale);
                log.info("✅ Catálogo guardado en Redis. Locales: {}", byLocale.keySet());
            }

        } catch (Exception e) {
            log.error("❌ Error cargando catálogo de notificaciones: {}", e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<NotificationCatalogDTO> getNotificationByKey(String key) {
        Cache redisCache = cacheManager.getCache(CACHE_NAME);
        Map<String, Map<String, NotificationCatalogDTO>> byLocale = null;

        if (redisCache != null) {
            Cache.ValueWrapper wrapper = redisCache.get(CACHE_KEY);
            if (wrapper != null) {
                byLocale = (Map<String, Map<String, NotificationCatalogDTO>>) wrapper.get();
            }
        }

        if (byLocale == null) {
            log.warn("⚠️ [Redis-Cache] Caché de notificaciones vacía. Forzando recarga...");
            loadCatalog();
            if (redisCache != null) {
                Cache.ValueWrapper wrapper = redisCache.get(CACHE_KEY);
                if (wrapper != null) {
                    byLocale = (Map<String, Map<String, NotificationCatalogDTO>>) wrapper.get();
                }
            }
        }

        if (byLocale == null) return Optional.empty();

        String currentLocale = LocaleContextHolder.getLocale().getLanguage();
        Map<String, NotificationCatalogDTO> localeMap = byLocale.get(currentLocale);

        if (localeMap == null) {
            localeMap = byLocale.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith(currentLocale))
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .orElse(null);
        }

        if (localeMap == null && !byLocale.isEmpty()) {
            localeMap = byLocale.values().iterator().next();
        }

        return localeMap != null ? Optional.ofNullable(localeMap.get(key)) : Optional.empty();
    }
}