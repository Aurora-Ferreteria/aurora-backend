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
            String urlConPaginacion = strapiUrl + "?populate=*&publicationState=preview&pagination[pageSize]=100";
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
        // 1. Obtener el código de idioma base (ej: "es", "en")
        String currentLanguage = LocaleContextHolder.getLocale().getLanguage();

        // 2. Intentar buscar el mapa de mensajes para ese idioma
        Map<String, String> localeMap = messageCache.get(currentLanguage);

        // 3. Si es nulo, buscar una clave que empiece por el idioma (ej: si busca "es" y tenemos "es-CO")
        if (localeMap == null) {
            String matchingKey = messageCache.keySet().stream()
                    .filter(key -> key.startsWith(currentLanguage))
                    .findFirst()
                    .orElse(null);

            if (matchingKey != null) {
                localeMap = messageCache.get(matchingKey);
            }
        }

        // 4. Si sigue siendo nulo, usar el primer idioma disponible en la caché como último recurso
        if (localeMap == null && !messageCache.isEmpty()) {
            localeMap = messageCache.values().iterator().next();
        }

        // 5. Retornar el mensaje o el nombre del enum si no se mapeó nada
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