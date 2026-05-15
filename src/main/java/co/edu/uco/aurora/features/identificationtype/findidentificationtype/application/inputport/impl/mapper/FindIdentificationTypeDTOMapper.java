package co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.inputport.impl.mapper;

import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.inputport.dto.FindIdentificationTypeDTO;
import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.usecase.domain.FindIdentificationTypeDomain;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FindIdentificationTypeDTOMapper {

    // Convertimos la lista de Dominios a lista de DTOs
    List<FindIdentificationTypeDTO> toDTOs(List<FindIdentificationTypeDomain> domains);
}