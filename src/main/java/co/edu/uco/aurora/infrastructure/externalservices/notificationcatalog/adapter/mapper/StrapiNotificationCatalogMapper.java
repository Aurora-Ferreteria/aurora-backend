package co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.adapter.mapper;

import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto.StrapiNotificationResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class StrapiNotificationCatalogMapper {

    public String extractTemplateValue(StrapiNotificationResponseDTO response) {
        if (response != null && response.getData() != null && !response.getData().isEmpty()) {
            return response.getData().get(0).getAttributes().getValue();
        }
        return null;
    }
}