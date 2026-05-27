package co.edu.uco.aurora.initializer.config;

import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.NotificationCatalog;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NotificationCatalogInitializer implements CommandLineRunner {

    private final NotificationCatalog notificationCatalog;

    public NotificationCatalogInitializer(NotificationCatalog notificationCatalog) {
        this.notificationCatalog = notificationCatalog;
    }

    @Override
    public void run(String... args) throws Exception {
        notificationCatalog.loadCatalog();
    }
}