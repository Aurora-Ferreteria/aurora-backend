package co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.adapter.mapper;

import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.dto.ParameterCatalogDTO;
import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.dto.StrapiParameterDataDTO;
import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.dto.StrapiParameterResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class StrapiParameterMapper {

    public Map<String, ParameterCatalogDTO> toMap(StrapiParameterResponseDTO response) {
        if (response == null || response.getData() == null) {
            return Collections.emptyMap();
        }

        Map<String, ParameterCatalogDTO> parameterMap = new HashMap<>();
        for (StrapiParameterDataDTO data : response.getData()) {
            if (data.getKey() != null) {
                // Guardamos el DTO serializable indexado por su KEY de Strapi
                parameterMap.put(data.getKey(), new ParameterCatalogDTO(data.getKey(), data.getValue()));
            }
        }
        return parameterMap;
    }
}