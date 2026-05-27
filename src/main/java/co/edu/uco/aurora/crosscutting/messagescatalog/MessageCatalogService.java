package co.edu.uco.aurora.crosscutting.messagescatalog;

public interface MessageCatalogService {

    String getMessageContent(MessagesEnum message);

    String getMessageContent(MessagesEnum message, String... params);

}