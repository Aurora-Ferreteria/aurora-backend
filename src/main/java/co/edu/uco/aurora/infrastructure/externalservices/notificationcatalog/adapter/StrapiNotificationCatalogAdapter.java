package co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.adapter;

import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.adapter.mapper.StrapiNotificationCatalogMapper;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto.StrapiNotificationResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StrapiNotificationCatalogAdapter {

    private final RestTemplate restTemplate;
    private final StrapiNotificationCatalogMapper mapper; // 1. Declaramos el mapper
    private final String STRAPI_URL = "https://magical-positivity-b27a86edda.strapiapp.com/api/messajes?filters[key][$eq]=";

    // 2. Lo inyectamos en el constructor
    public StrapiNotificationCatalogAdapter(StrapiNotificationCatalogMapper mapper) {
        this.restTemplate = new RestTemplate();
        this.mapper = mapper;
    }

    public String getTemplateValue(String templateKey) {
        String url = STRAPI_URL + templateKey;

        try {
            StrapiNotificationResponseDTO response = restTemplate.getForObject(url, StrapiNotificationResponseDTO.class);

            // 3. ¡Usamos el mapper para extraer el valor!
            String templateValue = mapper.extractTemplateValue(response);

            if (templateValue != null) {
                return templateValue;
            }
            throw new RuntimeException("No se encontró la plantilla en Strapi para la key: " + templateKey);

        } catch (Exception e) {
            throw new RuntimeException("Error conectando con el catálogo de notificaciones: " + e.getMessage());
        }
    }
}