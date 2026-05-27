package co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.adapter;

import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.NotificationCatalog;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.adapter.mapper.StrapiNotificationMapper;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto.NotificationCatalogDTO;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto.StrapiNotificationResponseDTO;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class StrapiNotificationCatalogAdapter implements NotificationCatalog {

    private final RestTemplate restTemplate;
    private final StrapiNotificationMapper mapper;
    private final CacheManager cacheManager; // 🔥 Inyectamos el manejador de caché oficial de Spring

    private final String STRAPI_URL = "https://magical-positivity-b27a86edda.strapiapp.com/api/notifications?pagination[limit]=100";

    public StrapiNotificationCatalogAdapter(RestTemplate restTemplate, StrapiNotificationMapper mapper, CacheManager cacheManager) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.cacheManager = cacheManager;
    }

    @Override
    public void loadCatalog() {
        try {
            System.out.println("🔄 [Redis-Cache] Cargando catálogo fresco desde Strapi...");
            StrapiNotificationResponseDTO response = restTemplate.getForObject(STRAPI_URL, StrapiNotificationResponseDTO.class);

            if (response != null && response.getData() != null) {
                Map<String, NotificationCatalogDTO> tempMap = new HashMap<>();

                response.getData().forEach(data -> {
                    NotificationCatalogDTO dto = mapper.toCatalogDto(data);
                    tempMap.put(dto.getKey(), dto);
                });

                Cache redisCache = cacheManager.getCache("notificationsCache");
                if (redisCache != null) {
                    redisCache.put("allNotifications", tempMap);
                }

                System.out.println("✅ Catálogo de Notificaciones v5 guardado con éxito en Redis. Total: " + tempMap.size());
            }
        } catch (Exception e) {
            System.err.println("❌ Error cargando catálogo de Strapi: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<NotificationCatalogDTO> getNotificationByKey(String key) {
        // 🔎 LEER DE REDIS: Intentamos recuperar el mapa del catálogo
        Cache redisCache = cacheManager.getCache("notificationsCache");

        if (redisCache != null) {
            Cache.ValueWrapper wrapper = redisCache.get("allNotifications");
            if (wrapper != null) {
                Map<String, NotificationCatalogDTO> cachedMap = (Map<String, NotificationCatalogDTO>) wrapper.get();
                if (cachedMap != null) {
                    return Optional.ofNullable(cachedMap.get(key));
                }
            }
        }

        System.out.println("⚠️ [Redis-Cache] La caché estaba vacía al solicitar la llave: " + key + ". Forzando recarga...");
        this.loadCatalog();

        if (redisCache != null) {
            Cache.ValueWrapper wrapper = redisCache.get("allNotifications");
            if (wrapper != null) {
                Map<String, NotificationCatalogDTO> cachedMap = (Map<String, NotificationCatalogDTO>) wrapper.get();
                if (cachedMap != null) {
                    return Optional.ofNullable(cachedMap.get(key));
                }
            }
        }

        return Optional.empty();
    }
}