package co.edu.uco.aurora.infrastructure.controller;

import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.features.customer.findcustomer.application.inputport.FindCustomerInputPort;
import co.edu.uco.aurora.features.customer.findcustomer.application.inputport.dto.FindCustomerDTO;
import co.edu.uco.aurora.infrastructure.controller.dto.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
        HttpStatusCode responseStatusCode = HttpStatus.OK;

        try {
            List<FindCustomerDTO> customers = inputPort.execute(null);
            responseObjectData.setData(customers);
            responseObjectData.addMessage(
                    MessagesEnum.FIND_CUSTOMER_SUCCESS.getContent()
            );

        } catch (final AuroraException exception) {
            responseObjectData = Response.createFailedResponse();
            responseObjectData.addMessage(exception.getUserMessage());
            responseStatusCode = HttpStatus.BAD_REQUEST;
            exception.printStackTrace();

        } catch (final Exception exception) {
            responseObjectData = Response.createFailedResponse();
            responseObjectData.addMessage(
                    MessagesEnum.FIND_CUSTOMER_ERROR.getContent()
            );
            responseStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            exception.printStackTrace();
        }

        return new ResponseEntity<>(responseObjectData, responseStatusCode);
    }
}