package co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.adapter;

import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.NotificationCatalog;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.adapter.mapper.StrapiNotificationMapper;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto.NotificationCatalogDTO;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto.StrapiNotificationResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate; // O el cliente que estés usando

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StrapiNotificationCatalogAdapter implements NotificationCatalog {

    private final Map<String, NotificationCatalogDTO> cache = new ConcurrentHashMap<>();

    private final RestTemplate restTemplate;
    private final StrapiNotificationMapper mapper;

    // NOTA: Para Strapi 5, si quieres asegurar que traiga más de 25 registros, puedes añadir '?pagination[limit]=100' al final de la URL
    private final String STRAPI_URL = "https://magical-positivity-b27a86edda.strapiapp.com/api/notifications?pagination[limit]=100";

    public StrapiNotificationCatalogAdapter(RestTemplate restTemplate, StrapiNotificationMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    @Override
    public void loadCatalog() {
        try {
            StrapiNotificationResponseDTO response = restTemplate.getForObject(STRAPI_URL, StrapiNotificationResponseDTO.class);

            if (response != null && response.getData() != null) {
                cache.clear();
                response.getData().forEach(data -> {
                    NotificationCatalogDTO dto = mapper.toCatalogDto(data);
                    cache.put(dto.getKey(), dto);
                });
                System.out.println("✅ Catálogo de Notificaciones v5 cargado. Total: " + cache.size());
            }
        } catch (Exception e) {
            System.err.println("❌ Error cargando catálogo de Strapi: " + e.getMessage());
        }
    }

    @Override
    public Optional<NotificationCatalogDTO> getNotificationByKey(String key) {
        return Optional.ofNullable(cache.get(key));
    }
}