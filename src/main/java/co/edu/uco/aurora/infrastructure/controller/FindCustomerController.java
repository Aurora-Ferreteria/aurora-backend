package co.edu.uco.aurora.infrastructure.controller;

import co.edu.uco.aurora.features.customer.findcustomer.application.inputport.FindCustomerInputPort;
import co.edu.uco.aurora.features.customer.findcustomer.application.inputport.dto.FindCustomerDTO;
import co.edu.uco.aurora.infrastructure.controller.dto.Response;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class FindCustomerController {

    private final FindCustomerInputPort inputPort;

    public FindCustomerController(FindCustomerInputPort inputPort) {
        this.inputPort = inputPort;
    }

    @GetMapping
    public ResponseEntity<Response<FindCustomerDTO>> findAllCustomers() {

        Response<FindCustomerDTO> responseObjectData = Response.createSuccededResponse();

        try {
            List<FindCustomerDTO> customers = inputPort.execute(null);

            responseObjectData.setData(customers);
            responseObjectData.getMessages().add(MessagesEnum.FIND_CUSTOMER_SUCCESS.getContent());
            return new ResponseEntity<>(responseObjectData, HttpStatus.OK);

        } catch (Exception e) {
            Response<FindCustomerDTO> errorResponse = Response.createFailedResponse();
            errorResponse.getMessages().add(MessagesEnum.FIND_CUSTOMER_ERROR.getContent());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}