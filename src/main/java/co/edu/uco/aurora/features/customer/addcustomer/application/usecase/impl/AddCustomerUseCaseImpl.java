package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.impl;

import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.AddCustomerUseCase;
import co.edu.uco.aurora.features.customer.addcustomer.application.usecase.domain.AddCustomerDomain;
import org.springframework.stereotype.Service;

@Service
public class AddCustomerUseCaseImpl implements AddCustomerUseCase {

    @Override
    public Void execute(AddCustomerDomain data) {
        return null;
    }
}
