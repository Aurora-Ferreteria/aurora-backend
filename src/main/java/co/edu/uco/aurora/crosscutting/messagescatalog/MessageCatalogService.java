package co.edu.uco.aurora.crosscutting.messagescatalog;

public interface MessageCatalogService {

    // Obtiene el mensaje puro tal cual viene de Strapi
    String getMessageContent(MessagesEnum message);

    // Obtiene el mensaje reemplazando los parámetros {0}, {1}, etc.
    String getMessageContent(MessagesEnum message, String... params);
}