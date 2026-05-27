package co.edu.uco.aurora.infrastructure.externalservices.notification.adapter.mapper;

import co.edu.uco.aurora.infrastructure.externalservices.notification.adapter.template.WelcomeEmailTemplate;
import co.edu.uco.aurora.infrastructure.externalservices.notification.dto.WelcomeEmailDTO;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.NotificationCatalog;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.stereotype.Component;

@Component
public class WelcomeEmailMapper {

    private final WelcomeEmailTemplate template;
    private final NotificationCatalog notificationCatalog;


    public WelcomeEmailMapper(WelcomeEmailTemplate template, NotificationCatalog notificationCatalog) {
        this.template = template;
        this.notificationCatalog = notificationCatalog;
    }

    public CreateEmailOptions toResendOptions(WelcomeEmailDTO dto) {

        String htmlBody = template.buildHtml(dto.getCustomerName());

        String sender = getMessage("SENDER");
        String subject = getMessage("SUBJECT");

        return CreateEmailOptions.builder()
                .from(sender)
                .to(dto.getToEmail())
                .subject(subject)
                .html(htmlBody)
                .build();
    }

    private String getMessage(String key) {
        return notificationCatalog.getNotificationByKey(key)
                .orElseThrow(() -> new RuntimeException("Falta la configuración en Strapi para la llave: " + key))
                .getValue();
    }
}