package co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog;

import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto.NotificationCatalogDTO;
import java.util.Optional;

public interface NotificationCatalog {
    void loadCatalog();
    Optional<NotificationCatalogDTO> getNotificationByKey(String key);
}