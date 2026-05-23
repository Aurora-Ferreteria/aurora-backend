package co.edu.uco.aurora.infrastructure.controller;

import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.features.customer.addcustomer.application.inputport.AddCustomerInputPort;
import co.edu.uco.aurora.features.customer.addcustomer.application.inputport.dto.AddCustomerDTO;
import co.edu.uco.aurora.infrastructure.controller.dto.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR')")
    public ResponseEntity<Response<AddCustomerDTO>> registerNewCustomer(@RequestBody AddCustomerDTO customer) {

        // 1. Ejecutamos el caso de uso (si falla, lanzará AuroraException y se irá al GlobalExceptionHandler)
        inputPort.execute(customer);

        // 2. Si todo sale bien, preparamos la respuesta de éxito
        Response<AddCustomerDTO> responseObjectData = Response.createSuccededResponse();

        // Nota: Si también quieres traducir los mensajes de éxito, podrías inyectar el MessageCatalogService aquí,
        // o manejarlo en un interceptor. Por ahora, dejamos tu llave de éxito.
        responseObjectData.addMessage(MessagesEnum.SUCCESS_OPERATION.name());

        return new ResponseEntity<>(responseObjectData, HttpStatus.CREATED);
    }
}