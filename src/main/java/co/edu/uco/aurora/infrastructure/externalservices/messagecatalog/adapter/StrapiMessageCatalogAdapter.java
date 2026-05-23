package co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.adapter;

import co.edu.uco.aurora.crosscutting.messagescatalog.MessageCatalogService;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.dto.StrapiMessageResponseDTO;
import co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.adapter.mapper.StrapiMessageMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import jakarta.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Component
public class StrapiMessageCatalogAdapter implements MessageCatalogService {

    // El mapa ahora tiene dos niveles: Idioma -> (Clave -> Valor)
    private Map<String, Map<String, String>> messageCache = new HashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.strapi.url}")
    private String strapiUrl;

    @PostConstruct
    public void initializeCache() {
        try {
            // Ahora strapiUrl viene limpio de Doppler. Le concatenamos TODOS los parámetros aquí.
            String urlConPaginacion = strapiUrl + "?populate=*&publicationState=preview&pagination[pageSize]=100&locale=all";

            System.out.println("[DEBUG] Intentando conectar a: " + urlConPaginacion);

            StrapiMessageResponseDTO response = restTemplate.getForObject(urlConPaginacion, StrapiMessageResponseDTO.class);

            if (response != null && response.getData() != null) {
                System.out.println("[DEBUG] Registros recibidos: " + response.getData().size());
                this.messageCache = StrapiMessageMapper.toI18nCacheMap(response);
                System.out.println("[Aurora] Catálogo i18n cargado exitosamente. Idiomas: " + this.messageCache.keySet());
            } else {
                System.out.println("[DEBUG] La respuesta de Strapi fue nula o data es nula");
            }

        } catch (Exception e) {
            System.err.println("[Error] No se pudo cargar el catálogo de Strapi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String getMessageContent(MessagesEnum message) {
        // Lee el idioma que envía el frontend en la cabecera
        String currentLanguage = LocaleContextHolder.getLocale().getLanguage();

        Map<String, String> localeMap = messageCache.getOrDefault(currentLanguage, messageCache.get("es"));

        if (localeMap != null) {
            return localeMap.getOrDefault(message.name(), message.name());
        }
        return message.name();
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