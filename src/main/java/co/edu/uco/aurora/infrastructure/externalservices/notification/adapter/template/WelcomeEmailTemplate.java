package co.edu.uco.aurora.infrastructure.externalservices.notification.adapter.template;

import co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.NotificationCatalog;
import org.springframework.stereotype.Component;

@Component
public class WelcomeEmailTemplate {

    private final NotificationCatalog notificationCatalog;

    public WelcomeEmailTemplate(NotificationCatalog notificationCatalog) {
        this.notificationCatalog = notificationCatalog;
    }

    public String buildHtml(String customerName) {
        // 1. Extraemos todos los textos de la caché (Strapi)
        String headerTitle = getMessage("WELCOME_EMAIL_HEADER_TITLE");
        String companyName = getMessage("COMPANY_NAME");
        String commitment = getMessage("WELCOME_EMAIL_COMMITMENT_TEXT");
        String dykTitle = getMessage("WELCOME_EMAIL_DID_YOU_KNOW_TITLE");
        String dykText = getMessage("WELCOME_EMAIL_DID_YOU_KNOW_TEXT");
        String farewell = getMessage("WELCOME_EMAIL_FAREWELL");
        String signature = getMessage("WELCOME_EMAIL_SIGNATURE");
        String footerWarning = getMessage("WELCOME_EMAIL_FOOTER_WARNING");
        String footerCopyright = getMessage("WELCOME_EMAIL_FOOTER_COPYRIGHT");

        // 2. Inyectamos las variables en el HTML preservando el diseño
        return """
            <!DOCTYPE html>
            <html lang="es">
            <body style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f7f6; margin: 0; padding: 40px 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 15px rgba(0,0,0,0.05);">
                    <div style="background-color: #5651e5; padding: 30px; text-align: center;">
                        <h1 style="color: #ffffff; margin: 0; font-size: 28px; letter-spacing: 1px;">%s</h1>
                    </div>
                    <div style="padding: 40px 30px; color: #4a4a4a; line-height: 1.8;">
                        <p style="font-size: 18px; margin-top: 0;">Hola <strong style="color: #5651e5;">%s</strong>,</p>
                        <p>Nos alegra informarte que tus datos han sido registrados exitosamente en nuestro sistema de clientes. A partir de ahora, agilizaremos todas tus compras, cotizaciones y pedidos.</p>
                        <p>En <strong>%s</strong> %s</p>
                        <div style="background-color: #f8f9fa; border-left: 4px solid #5651e5; padding: 15px; margin: 30px 0; border-radius: 4px;">
                            <p style="margin: 0; font-size: 15px;"><strong>%s</strong> %s</p>
                        </div>
                        <p style="margin-bottom: 0;">%s</p>
                        <p style="margin-top: 5px;"><strong>%s</strong></p>
                    </div>
                    <div style="background-color: #ecf0f1; padding: 20px; text-align: center; font-size: 12px; color: #7f8c8d;">
                        <p style="margin: 0 0 10px 0;">%s</p>
                        <p style="margin: 0;">&copy; %s</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                headerTitle, customerName, companyName, commitment,
                dykTitle, dykText, farewell, signature, footerWarning, footerCopyright
        );
    }

    // Método auxiliar privado para buscar las llaves más fácilmente
    private String getMessage(String key) {
        return notificationCatalog.getNotificationByKey(key)
                .orElseThrow(() -> new RuntimeException("Falta la configuración en Strapi para la llave: " + key))
                .getValue();
    }
}