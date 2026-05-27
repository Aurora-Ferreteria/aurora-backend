package co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.adapter;

import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.NotificationCatalog;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.adapter.mapper.StrapiNotificationMapper;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto.NotificationCatalogDTO;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto.StrapiNotificationResponseDTO;
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

    private final RestTemplate restTemplate;
    private final StrapiNotificationMapper mapper;
    private final CacheManager cacheManager;

    private final String STRAPI_URL = "https://magical-positivity-b27a86edda.strapiapp.com/api/notifications?pagination[limit]=100";

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
            System.out.println("🔄 [Redis-Cache] Cargando catálogo de notificaciones desde Strapi...");

            Map<String, Map<String, NotificationCatalogDTO>> byLocale = new HashMap<>();

            // Consultar cada locale por separado
            for (String locale : List.of("es", "en")) {
                String url = STRAPI_URL + "&locale=" + locale;
                StrapiNotificationResponseDTO response = restTemplate.getForObject(url, StrapiNotificationResponseDTO.class);

                if (response != null && response.getData() != null) {
                    Map<String, NotificationCatalogDTO> localeMap = new HashMap<>();
                    response.getData().forEach(data -> {
                        NotificationCatalogDTO dto = mapper.toCatalogDto(data);
                        localeMap.put(dto.getKey(), dto);
                    });
                    byLocale.put(locale, localeMap);
                    System.out.println("✅ Notificaciones [" + locale + "] cargadas: " + localeMap.size());
                }
            }

            Cache redisCache = cacheManager.getCache("notificationsCache");
            if (redisCache != null) {
                redisCache.put("allNotifications", byLocale);
                System.out.println("✅ Catálogo guardado en Redis. Locales: " + byLocale.keySet());
            }

        } catch (Exception e) {
            System.err.println("❌ Error cargando catálogo de notificaciones: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<NotificationCatalogDTO> getNotificationByKey(String key) {
        Cache redisCache = cacheManager.getCache("notificationsCache");
        Map<String, Map<String, NotificationCatalogDTO>> byLocale = null;

        if (redisCache != null) {
            Cache.ValueWrapper wrapper = redisCache.get("allNotifications");
            if (wrapper != null) {
                byLocale = (Map<String, Map<String, NotificationCatalogDTO>>) wrapper.get();
            }
        }

        if (byLocale == null) {
            System.out.println("⚠️ [Redis-Cache] Caché de notificaciones vacía. Forzando recarga...");
            loadCatalog();
            if (redisCache != null) {
                Cache.ValueWrapper wrapper = redisCache.get("allNotifications");
                if (wrapper != null) {
                    byLocale = (Map<String, Map<String, NotificationCatalogDTO>>) wrapper.get();
                }
            }
        }

        if (byLocale == null) return Optional.empty();

        String currentLocale = LocaleContextHolder.getLocale().getLanguage(); // "es" o "en"

        Map<String, NotificationCatalogDTO> localeMap = byLocale.get(currentLocale);

        if (localeMap == null) {
            localeMap = byLocale.entrySet().stream()
                    .filter(e -> e.getKey().startsWith(currentLocale))
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