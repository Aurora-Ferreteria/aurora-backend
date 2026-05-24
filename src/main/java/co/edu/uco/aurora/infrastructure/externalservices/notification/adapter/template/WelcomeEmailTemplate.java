package co.edu.uco.aurora.infrastructure.externalservices.notification.adapter.template;

public class WelcomeEmailTemplate {

    private WelcomeEmailTemplate() {
    }

    public static String buildHtml(String htmlFromStrapi, String customerName) {
        // Recibe el HTML completo de Strapi y solo inyecta el nombre del cliente en el %s
        return String.format(htmlFromStrapi, customerName);
    }
}