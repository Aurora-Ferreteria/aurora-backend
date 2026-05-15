package co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.usecase;

import co.edu.uco.aurora.application.usecase.UseCase;
import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.usecase.domain.FindIdentificationTypeDomain;

import java.util.List;

public interface FindIdentificationTypeUseCase extends UseCase<Void, List<FindIdentificationTypeDomain>> {
}