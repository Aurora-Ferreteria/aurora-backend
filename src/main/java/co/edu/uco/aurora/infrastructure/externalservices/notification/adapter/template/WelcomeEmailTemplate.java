package co.edu.uco.aurora.infrastructure.externalservices.notification.adapter.template;

public class WelcomeEmailTemplate {

    private WelcomeEmailTemplate() {
    }

    public static String buildHtml(
            String customerName, String headerTitle, String companyName,
            String commitmentText, String didYouKnowTitle, String didYouKnowText,
            String farewell, String signature, String footerWarning, String footerCopyright) {

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
                headerTitle, customerName, companyName, commitmentText,
                didYouKnowTitle, didYouKnowText, farewell, signature,
                footerWarning, footerCopyright
        );
    }
}