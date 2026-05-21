package co.edu.uco.aurora.infrastructure.externalservices.resend;

import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.helper.TextHelper;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.application.usecase.WelcomeEmailSender;
import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResendEmailService implements WelcomeEmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResendEmailService.class);

    @Value("${api.resend.key}")
    private String resendApiKey;

    @Override
    public void sendWelcomeEmail(String toEmail, String customerName) {
        Resend resend = new Resend(resendApiKey);

        String htmlBody = """
            <!DOCTYPE html>
            <html lang="es">
            <body style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f7f6; margin: 0; padding: 40px 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 15px rgba(0,0,0,0.05);">
                    <div style="background-color: #5651e5; padding: 30px; text-align: center;">
                        <h1 style="color: #ffffff; margin: 0; font-size: 28px; letter-spacing: 1px;">¡Bienvenido a Aurora! 🛠️</h1>
                    </div>
                    <div style="padding: 40px 30px; color: #4a4a4a; line-height: 1.8;">
                        <p style="font-size: 18px; margin-top: 0;">Hola <strong style="color: #5651e5;">%s</strong>,</p>
                        <p>Nos alegra informarte que tus datos han sido registrados exitosamente en nuestro sistema de clientes. A partir de ahora, agilizaremos todas tus compras, cotizaciones y pedidos.</p>
                        <p>En <strong>Ferretería Aurora</strong> estamos comprometidos con brindarte los mejores materiales y herramientas para que todos tus proyectos sean un éxito total.</p>
                        <div style="background-color: #f8f9fa; border-left: 4px solid #5651e5; padding: 15px; margin: 30px 0; border-radius: 4px;">
                            <p style="margin: 0; font-size: 15px;"><strong>¿Sabías qué?</strong> Al estar registrado en nuestro sistema, podremos ofrecerte una atención mucho más rápida y personalizada cada vez que nos visites.</p>
                        </div>
                        <p style="margin-bottom: 0;">¡Esperamos poder atenderte muy pronto en nuestras instalaciones!</p>
                        <p style="margin-top: 5px;"><strong>El equipo de Ferretería Aurora</strong></p>
                    </div>
                    <div style="background-color: #ecf0f1; padding: 20px; text-align: center; font-size: 12px; color: #7f8c8d;">
                        <p style="margin: 0 0 10px 0;">Este es un mensaje automático de confirmación de registro.</p>
                        <p style="margin: 0;">&copy; 2026 Ferretería Aurora. Todos los derechos reservados.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(customerName);

        CreateEmailOptions options = CreateEmailOptions.builder()
                .from("Ferretería Aurora <administradores@aurorajpkd.com>")
                .to(toEmail)
                .subject("¡Estás registrado en Ferretería Aurora! 🛠️")
                .html(htmlBody)
                .build();

        try {
            CreateEmailResponse data = resend.emails().send(options);
            LOGGER.info("Correo de bienvenida enviado con éxito. ID de Resend: {}", data.getId());

        } catch (Exception e) {
            var userMessage = MessagesEnum.RESEND_SERVICE_SENDING_ERROR.getTitle();
            var technicalMessage = TextHelper.format(
                    MessagesEnum.RESEND_SERVICE_SENDING_ERROR.getContent(),
                    e.getMessage()
            );
            throw AuroraException.create(userMessage, technicalMessage);
        }
    }
}