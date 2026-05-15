package co.edu.uco.aurora.features.customer.findcustomer.application.usecase;

import co.edu.uco.aurora.application.usecase.UseCase;
import co.edu.uco.aurora.features.customer.findcustomer.application.usecase.domain.FindCustomerDomain;

import java.util.List;

public interface FindCustomerUseCase extends UseCase<Void, List<FindCustomerDomain>> {
}