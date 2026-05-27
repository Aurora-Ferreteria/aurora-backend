package co.edu.uco.aurora.crosscutting.messagescatalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageCatalogProvider {

    private static MessageCatalogService service;

    private MessageCatalogProvider() {
        super();
    }

    @Autowired
    public void setServiceInstance(MessageCatalogService service) {
        setService(service);
    }

    private static void setService(MessageCatalogService service) {
        MessageCatalogProvider.service = service;
    }

    public static MessageCatalogService getService() {
        return service;
    }
}