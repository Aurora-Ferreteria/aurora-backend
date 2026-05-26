package co.edu.uco.aurora.crosscutting.messagescatalog;

import org.springframework.stereotype.Component;

@Component
public class MessageCatalogProvider {

    private static MessageCatalogService service;

    public MessageCatalogProvider(MessageCatalogService service) {
        MessageCatalogProvider.service = service;
    }

    public static MessageCatalogService getService() {
        return service;
    }
}