package co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.adapter;

import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.ParameterCatalog;
import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.adapter.mapper.StrapiParameterMapper;
import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.dto.ParameterCatalogDTO;
import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.dto.StrapiParameterResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class StrapiParameterCatalogAdapter implements ParameterCatalog {

    private final RestTemplate restTemplate;
    private final StrapiParameterMapper mapper;
    private final CacheManager cacheManager;

    private final String STRAPI_URL = "https://popular-nest-1d97439245.strapiapp.com/api/parameters?pagination[limit]=100";

    public StrapiParameterCatalogAdapter(RestTemplate restTemplate,
                                         StrapiParameterMapper mapper,
                                         CacheManager cacheManager) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.cacheManager = cacheManager;
    }

    @Override
    public void loadParameters() {
        try {
            // Consumir Strapi Cloud usando RestTemplate nativo de tu app
            StrapiParameterResponseDTO response = restTemplate.getForObject(STRAPI_URL, StrapiParameterResponseDTO.class);

            // Mapear al formato de mapa para Redis
            Map<String, ParameterCatalogDTO> parameterMap = mapper.toMap(response);

            // Guardar en la caché de Redis
            Cache cache = cacheManager.getCache("parameterCache");
            if (cache != null) {
                cache.put("allParameters", parameterMap);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error crítico al inicializar el catálogo de parámetros desde Strapi Cloud", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getParameterValue(String key) {
        Cache cache = cacheManager.getCache("parameterCache");
        Map<String, ParameterCatalogDTO> parameterMap = null;

        // 1. Intentar leer desde Redis
        if (cache != null) {
            Cache.ValueWrapper wrapper = cache.get("allParameters");
            if (wrapper != null) {
                parameterMap = (Map<String, ParameterCatalogDTO>) wrapper.get();
            }
        }

        // 2. Si la caché está vacía, viaja a Strapi Cloud a recargar
        if (parameterMap == null) {
            loadParameters();
            if (cache != null) {
                Cache.ValueWrapper wrapper = cache.get("allParameters");
                if (wrapper != null) {
                    parameterMap = (Map<String, ParameterCatalogDTO>) wrapper.get();
                }
            }
        }

        // 3. Retornar el valor de la regex correspondiente
        if (parameterMap != null && parameterMap.containsKey(key)) {
            return parameterMap.get(key).getValue();
        }

        return null;
    }
}