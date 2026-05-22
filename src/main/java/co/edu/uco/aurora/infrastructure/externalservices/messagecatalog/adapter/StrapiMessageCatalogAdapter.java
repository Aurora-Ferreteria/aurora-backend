package co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.adapter;

import co.edu.uco.aurora.crosscutting.messagescatalog.MessageCatalogService;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.dto.StrapiMessageResponseDTO;
import co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.adapter.mapper.StrapiMessageMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import jakarta.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Component
public class StrapiMessageCatalogAdapter implements MessageCatalogService {

    private Map<String, String> messageCache = new HashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${api.strapi.url}")
    private String strapiUrl;

    @PostConstruct
    public void initializeCache() {
        try {
            // 1. Va a internet y trae el DTO
            StrapiMessageResponseDTO response = restTemplate.getForObject(strapiUrl, StrapiMessageResponseDTO.class);

            // 2. Usa tu MAPPER para limpiar la basura de Strapi y quedarse solo con los datos útiles
            this.messageCache = StrapiMessageMapper.toCacheMap(response);

            System.out.println("[Aurora] Catálogo de mensajes cargado exitosamente desde Strapi.");
        } catch (Exception e) {
            System.err.println("[Error] No se pudo cargar el catálogo de Strapi: " + e.getMessage());
        }
    }

    @Override
    public String getMessageContent(MessagesEnum message) {
        return messageCache.getOrDefault(message.name(), message.name());
    }

    @Override
    public String getMessageContent(MessagesEnum message, String... params) {
        String pattern = getMessageContent(message);
        try {
            return MessageFormat.format(pattern, (Object[]) params);
        } catch (IllegalArgumentException e) {
            return pattern;
        }
    }
}