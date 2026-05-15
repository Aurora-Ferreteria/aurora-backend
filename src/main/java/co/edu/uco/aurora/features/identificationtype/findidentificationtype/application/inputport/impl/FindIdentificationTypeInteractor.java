package co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.inputport.impl;

import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.inputport.FindIdentificationTypeInputPort;
import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.inputport.dto.FindIdentificationTypeDTO;
import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.inputport.impl.mapper.FindIdentificationTypeDTOMapper;
import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.usecase.FindIdentificationTypeUseCase;
import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.usecase.domain.FindIdentificationTypeDomain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FindIdentificationTypeInteractor implements FindIdentificationTypeInputPort {

    private final FindIdentificationTypeUseCase useCase;
    private final FindIdentificationTypeDTOMapper mapper;

    public FindIdentificationTypeInteractor(FindIdentificationTypeUseCase useCase, FindIdentificationTypeDTOMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @Override
    public List<FindIdentificationTypeDTO> execute(Void data) {
        List<FindIdentificationTypeDomain> domains = useCase.execute(null);
        return mapper.toDTOs(domains);
    }
}