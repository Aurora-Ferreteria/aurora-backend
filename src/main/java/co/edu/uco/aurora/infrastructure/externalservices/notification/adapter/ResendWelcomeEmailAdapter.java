package co.edu.uco.aurora.infrastructure.externalservices.notification.adapter;

import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.helper.TextHelper;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.infrastructure.externalservices.notification.WelcomeEmailSender;
import co.edu.uco.aurora.infrastructure.externalservices.notification.adapter.mapper.WelcomeEmailMapper;
import co.edu.uco.aurora.infrastructure.externalservices.notification.dto.WelcomeEmailDTO;

import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResendWelcomeEmailAdapter implements WelcomeEmailSender {

    private final String resendApiKey;
    private final WelcomeEmailMapper mapper;

    public ResendWelcomeEmailAdapter(@Value("${api.resend.key}") String resendApiKey,
                                     WelcomeEmailMapper mapper) {
        this.resendApiKey = resendApiKey;
        this.mapper = mapper;
    }

    @Override
    public void sendWelcomeEmail(WelcomeEmailDTO emailDto) {
        try {
            Resend resend = new Resend(resendApiKey);

            CreateEmailOptions options = mapper.toResendOptions(emailDto);

            resend.emails().send(options);

        } catch (Exception e) {
            var userMessage = MessagesEnum.RESEND_SERVICE_SENDING_ERROR.getMessage();
            throw AuroraException.create(userMessage);
        }
    }
}