package co.edu.uco.aurora.infrastructure.controller;

import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.features.customer.addcustomer.application.inputport.AddCustomerInputPort;
import co.edu.uco.aurora.features.customer.addcustomer.application.inputport.dto.AddCustomerDTO;
import co.edu.uco.aurora.infrastructure.controller.dto.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class AddCustomerController {

    private final AddCustomerInputPort inputPort;

    public AddCustomerController(AddCustomerInputPort inputPort) {
        this.inputPort = inputPort;
    }

    @PostMapping
    public ResponseEntity<Response<AddCustomerDTO>> registerNewCustomer(@RequestBody AddCustomerDTO customer) {

        Response<AddCustomerDTO> responseObjectData = Response.createSuccededResponse();
        HttpStatusCode responseStatusCode = HttpStatus.CREATED;

        try {
            inputPort.execute(customer);

            responseObjectData.addMessage(
                    MessagesEnum.CUSTOMER_REGISTER_SUCCESS.getContent()
            );

        } catch (final AuroraException exception) {
            responseObjectData = Response.createFailedResponse();
            responseObjectData.addMessage(exception.getUserMessage());
            responseStatusCode = HttpStatus.BAD_REQUEST;
            exception.printStackTrace();

        } catch (final Exception exception) {
            responseObjectData = Response.createFailedResponse();
            responseObjectData.addMessage(
                    MessagesEnum.CUSTOMERS_UNEXPECTED_ERROR.getContent()
            );
            responseStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            exception.printStackTrace();
        }

        return new ResponseEntity<>(responseObjectData, responseStatusCode);
    }
}