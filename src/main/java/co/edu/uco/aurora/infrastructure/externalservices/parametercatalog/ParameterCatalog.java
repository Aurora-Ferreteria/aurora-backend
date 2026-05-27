package co.edu.uco.aurora.infrastructure.externalservices.parametercatalog;

import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.dto.ParameterCatalogDTO;
import java.util.Map;

public interface ParameterCatalog {

    Map<String, ParameterCatalogDTO> loadParameters();

    String getParameterValue(String key);
}