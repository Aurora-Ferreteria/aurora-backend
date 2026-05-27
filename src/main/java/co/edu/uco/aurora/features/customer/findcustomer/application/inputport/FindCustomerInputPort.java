package co.edu.uco.aurora.features.customer.findcustomer.application.inputport;

import co.edu.uco.aurora.application.inputport.InputPort;
import co.edu.uco.aurora.features.customer.findcustomer.application.inputport.dto.FindCustomerDTO;

import java.util.List;

public interface FindCustomerInputPort extends InputPort<Void, List<FindCustomerDTO>> {

}