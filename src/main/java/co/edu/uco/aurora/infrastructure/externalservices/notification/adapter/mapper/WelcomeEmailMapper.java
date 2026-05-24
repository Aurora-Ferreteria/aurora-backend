package co.edu.uco.aurora.infrastructure.externalservices.notification.adapter.mapper;

import co.edu.uco.aurora.infrastructure.externalservices.notification.dto.WelcomeEmailDTO;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.stereotype.Component;

@Component
public class WelcomeEmailMapper {

    private static final String SENDER = "Ferretería Aurora <administradores@aurorajpkd.com>";
    private static final String SUBJECT = "¡Estás registrado en Ferretería Aurora! 🛠️";

    public CreateEmailOptions toResendOptions(WelcomeEmailDTO dto, String finalHtml) {

        return CreateEmailOptions.builder()
                .from(SENDER)
                .to(dto.getToEmail())
                .subject(SUBJECT)
                .html(finalHtml)
                .build();
    }
}
