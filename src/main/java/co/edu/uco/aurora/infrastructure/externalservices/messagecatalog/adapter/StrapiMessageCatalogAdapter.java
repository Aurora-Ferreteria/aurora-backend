package co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.adapter;

import co.edu.uco.aurora.crosscutting.messagescatalog.MessageCatalogService;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.dto.StrapiMessageResponseDTO;
import co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.adapter.mapper.StrapiMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import jakarta.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Component
public class StrapiMessageCatalogAdapter implements MessageCatalogService {
    private final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private StringRedisTemplate redisTemplate; // Este objeto maneja la conexión
    @PostConstruct
    public void verificarConexion() {
        try {
            redisTemplate.opsForValue().set("test_aurora", "Conexion Exitosa");
            String valor = redisTemplate.opsForValue().get("test_aurora");
            System.out.println("[Aurora-Redis] ✅ Conexión verificada: " + valor);
        } catch (Exception e) {
            System.err.println("[Aurora-Redis] ❌ Error conectando a Redis: " + e.getMessage());
        }
    }

    @Value("${api.strapi.url}")
    private String strapiUrl;

    // 1. Este método consulta a Strapi y guarda el resultado en caché automáticamente.
    @Cacheable(value = "messageCatalog", key = "'allMessages'")
    public Map<String, Map<String, String>> loadAllMessagesFromStrapi() {
        String url = strapiUrl + "?populate=*&publicationState=preview&pagination[pageSize]=100";
        StrapiMessageResponseDTO response = restTemplate.getForObject(url, StrapiMessageResponseDTO.class);
        return StrapiMessageMapper.toI18nCacheMap(response);
    }

    // 2. Úsalo cuando necesites forzar la actualización de los mensajes desde Strapi.
    @CacheEvict(value = "messageCatalog", key = "'allMessages'")
    public void clearCache() {
        // Al llamarlo, se vacía la caché y la próxima petición recargará todo.
    }

    @Override
    public String getMessageContent(MessagesEnum message) {
        // 3. Obtenemos el mapa de la caché (automático)
        Map<String, Map<String, String>> messageCache = loadAllMessagesFromStrapi();

        // Lógica de búsqueda (igual que antes)
        String currentLanguage = LocaleContextHolder.getLocale().getLanguage();
        Map<String, String> localeMap = messageCache.get(currentLanguage);

        if (localeMap == null) {
            String matchingKey = messageCache.keySet().stream()
                    .filter(key -> key.startsWith(currentLanguage))
                    .findFirst()
                    .orElse(null);
            if (matchingKey != null) {
                localeMap = messageCache.get(matchingKey);
            }
        }

        if (localeMap == null && !messageCache.isEmpty()) {
            localeMap = messageCache.values().iterator().next();
        }

        return (localeMap != null) ? localeMap.getOrDefault(message.name(), message.name()) : message.name();
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