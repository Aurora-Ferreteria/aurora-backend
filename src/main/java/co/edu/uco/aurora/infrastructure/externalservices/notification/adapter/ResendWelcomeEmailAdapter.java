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
            String rawHtmlFromStrapi = strapiAdapter.getTemplateValue("WELCOME_EMAIL_HTML");
            String finalHtml = WelcomeEmailTemplate.buildHtml(rawHtmlFromStrapi, emailDto.getCustomerName());
            Resend resend = new Resend(resendApiKey);

            CreateEmailOptions options = mapper.toResendOptions(emailDto, finalHtml);

            resend.emails().send(options);

        } catch (Exception e) {
            var userMessage = MessagesEnum.RESEND_SERVICE_SENDING_ERROR.name();
            throw AuroraException.create(userMessage);
        }
    }
}