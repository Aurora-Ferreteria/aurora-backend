package co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.adapter;

import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.ParameterCatalog;
import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.adapter.mapper.StrapiParameterMapper;
import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.dto.ParameterCatalogDTO;
import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.dto.StrapiParameterResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class StrapiParameterCatalogAdapter implements ParameterCatalog {

    private static final Logger log = LoggerFactory.getLogger(StrapiParameterCatalogAdapter.class);

    private static final String CACHE_NAME = "parameterCache";
    private static final String CACHE_KEY = "allParameters";

    private final RestTemplate restTemplate;
    private final StrapiParameterMapper mapper;
    private final CacheManager cacheManager;

    @Value("${api.strapi.parameters.url}")
    private String strapiParametersUrl;

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
            log.warn("⚠️ [Redis-Cache] Caché de parámetros vacía. Forzando recarga desde Strapi...");
            StrapiParameterResponseDTO response = restTemplate.getForObject(strapiParametersUrl, StrapiParameterResponseDTO.class);

            if (response == null) {
                log.error("❌ Respuesta de Strapi vacía para Parámetros.");
                return Collections.emptyMap();
            }

            Map<String, ParameterCatalogDTO> parameterMap = mapper.toMap(response);

            Cache cache = cacheManager.getCache(CACHE_NAME);
            if (cache != null && parameterMap != null) {
                cache.put(CACHE_KEY, parameterMap);
                log.info("✅ Parámetros guardados en Redis con éxito. Total: {}", parameterMap.size());
            }

            return parameterMap != null ? parameterMap : Collections.emptyMap();

        } catch (Exception e) {
            log.error("❌ Error crítico al inicializar el catálogo de parámetros desde Strapi: {}", e.getMessage());
            return Collections.emptyMap();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getParameterValue(String key) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        Map<String, ParameterCatalogDTO> parameterMap = null;

        if (cache != null) {
            Cache.ValueWrapper wrapper = cache.get(CACHE_KEY);
            if (wrapper != null) {
                parameterMap = (Map<String, ParameterCatalogDTO>) wrapper.get();
            }
        }

        if (parameterMap == null || parameterMap.isEmpty()) {
            parameterMap = loadParameters();
        }

        if (parameterMap != null && parameterMap.containsKey(key)) {
            return parameterMap.get(key).getValue();
        }

        return null;
    }
}