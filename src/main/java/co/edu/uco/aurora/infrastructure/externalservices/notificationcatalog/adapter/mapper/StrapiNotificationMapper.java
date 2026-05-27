package co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.adapter.mapper;

import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto.NotificationCatalogDTO;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto.StrapiNotificationDataDTO;
import org.springframework.stereotype.Component;

@Component
public class StrapiNotificationMapper {

    public NotificationCatalogDTO toCatalogDto(StrapiNotificationDataDTO strapiData) {
        return new NotificationCatalogDTO(
                strapiData.getKey(),
                strapiData.getValue()
        );
    }
}