package co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.adapter;

import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.ParameterCatalog;
import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.adapter.mapper.StrapiParameterMapper;
import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.dto.ParameterCatalogDTO;
import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.dto.StrapiParameterResponseDTO;
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
    public Map<String, ParameterCatalogDTO> loadParameters() {
        try {
            System.out.println("⚠️ [Redis-Cache] Caché de parámetros vacía. Forzando recarga desde Strapi...");
            StrapiParameterResponseDTO response = restTemplate.getForObject(STRAPI_URL, StrapiParameterResponseDTO.class);

            if (response == null) {
                System.err.println("❌ Respuesta de Strapi vacía para Parámetros.");
                return null;
            }

            Map<String, ParameterCatalogDTO> parameterMap = mapper.toMap(response);

            Cache cache = cacheManager.getCache("parameterCache");
            if (cache != null && parameterMap != null) {
                cache.put("allParameters", parameterMap);
                System.out.println("✅ Parámetros guardados en Redis con éxito. Total: " + parameterMap.size());
            }

            return parameterMap;
        } catch (Exception e) {
            System.err.println("❌ Error crítico al inicializar el catálogo de parámetros desde Strapi: " + e.getMessage());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getParameterValue(String key) {
        Cache cache = cacheManager.getCache("parameterCache");
        Map<String, ParameterCatalogDTO> parameterMap = null;

        if (cache != null) {
            Cache.ValueWrapper wrapper = cache.get("allParameters");
            if (wrapper != null) {
                parameterMap = (Map<String, ParameterCatalogDTO>) wrapper.get();
            }
        }

        if (parameterMap == null) {
            parameterMap = loadParameters();
        }

        if (parameterMap != null && parameterMap.containsKey(key)) {
            return parameterMap.get(key).getValue();
        }

        return null;
    }
}