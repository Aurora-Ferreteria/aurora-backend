package co.edu.uco.aurora.infrastructure.externalservices.notification.adapter.mapper;

import co.edu.uco.aurora.infrastructure.externalservices.notification.adapter.template.WelcomeEmailTemplate;
import co.edu.uco.aurora.infrastructure.externalservices.notification.dto.WelcomeEmailDTO;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.stereotype.Component;

@Component
public class WelcomeEmailMapper {

    private static final String SENDER = "Ferretería Aurora <administradores@aurorajpkd.com>";
    private static final String SUBJECT = "¡Estás registrado en Ferretería Aurora! 🛠️";

    // 1. Declaramos el template como una dependencia
    private final WelcomeEmailTemplate template;

    // 2. Lo inyectamos a través del constructor
    public WelcomeEmailMapper(WelcomeEmailTemplate template) {
        this.template = template;
    }

    public CreateEmailOptions toResendOptions(WelcomeEmailDTO dto) {

        // 3. Llamamos al método usando la instancia inyectada (minúscula), no la clase (mayúscula)
        String htmlBody = template.buildHtml(dto.getCustomerName());

        return CreateEmailOptions.builder()
                .from(SENDER)
                .to(dto.getToEmail())
                .subject(SUBJECT)
                .html(htmlBody)
                .build();
    }
}