package co.edu.uco.aurora.infrastructure.externalservices.notification.adapter;

import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.infrastructure.externalservices.notification.WelcomeEmailSender;
import co.edu.uco.aurora.infrastructure.externalservices.notification.adapter.mapper.WelcomeEmailMapper;
import co.edu.uco.aurora.infrastructure.externalservices.notification.adapter.template.WelcomeEmailTemplate;
import co.edu.uco.aurora.infrastructure.externalservices.notification.dto.WelcomeEmailDTO;
import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.adapter.StrapiNotificationCatalogAdapter;
import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResendWelcomeEmailAdapter implements WelcomeEmailSender {

    private final String resendApiKey;
    private final WelcomeEmailMapper mapper;
    private final StrapiNotificationCatalogAdapter strapiAdapter;

    public ResendWelcomeEmailAdapter(@Value("${api.resend.key}") String resendApiKey,
                                     WelcomeEmailMapper mapper,
                                     StrapiNotificationCatalogAdapter strapiAdapter) {
        this.resendApiKey = resendApiKey;
        this.mapper = mapper;
        this.strapiAdapter = strapiAdapter;
    }

    @Override
    public void sendWelcomeEmail(WelcomeEmailDTO emailDto) {
        try {
            // 1. Extraemos cada fragmento de texto de Strapi usando su Key correspondiente
            String headerTitle = strapiAdapter.getTemplateValue("WELCOME_EMAIL_HEADER_TITLE");
            String companyName = strapiAdapter.getTemplateValue("COMPANY_NAME");
            String commitmentText = strapiAdapter.getTemplateValue("WELCOME_EMAIL_COMMITMENT_TEXT");
            String didYouKnowTitle = strapiAdapter.getTemplateValue("WELCOME_EMAIL_DID_YOU_KNOW_TITLE");
            String didYouKnowText = strapiAdapter.getTemplateValue("WELCOME_EMAIL_DID_YOU_KNOW_TEXT");
            String farewell = strapiAdapter.getTemplateValue("WELCOME_EMAIL_FAREWELL");
            String signature = strapiAdapter.getTemplateValue("WELCOME_EMAIL_SIGNATURE");
            String footerWarning = strapiAdapter.getTemplateValue("WELCOME_EMAIL_FOOTER_WARNING");
            String footerCopyright = strapiAdapter.getTemplateValue("WELCOME_EMAIL_FOOTER_COPYRIGHT");

            // 2. Construimos el HTML final pasando los 10 parámetros en orden
            String finalHtml = WelcomeEmailTemplate.buildHtml(
                    emailDto.getCustomerName(), // El nombre viene del registro, no de Strapi
                    headerTitle,
                    companyName,
                    commitmentText,
                    didYouKnowTitle,
                    didYouKnowText,
                    farewell,
                    signature,
                    footerWarning,
                    footerCopyright
            );

            // 3. Enviamos el correo con Resend
            Resend resend = new Resend(resendApiKey);
            CreateEmailOptions options = mapper.toResendOptions(emailDto, finalHtml);
            resend.emails().send(options);

        } catch (Exception e) {
            var userMessage = MessagesEnum.RESEND_SERVICE_SENDING_ERROR.name();
            throw AuroraException.create(userMessage);
        }
    }
}