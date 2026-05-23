package co.edu.uco.aurora.infrastructure.controller;

import co.edu.uco.aurora.crosscutting.messagescatalog.MessageCatalogService; // <-- Importante
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
    private final MessageCatalogService messageCatalogService; // <-- Agregado

    // Inyectamos el servicio en el constructor
    public AddCustomerController(AddCustomerInputPort inputPort, MessageCatalogService messageCatalogService) {
        this.inputPort = inputPort;
        this.messageCatalogService = messageCatalogService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR')")
    public ResponseEntity<Response<AddCustomerDTO>> registerNewCustomer(@RequestBody AddCustomerDTO customer) {

        // 1. Ejecutamos el caso de uso
        inputPort.execute(customer);

        // 2. Si todo sale bien, preparamos la respuesta de éxito
        Response<AddCustomerDTO> responseObjectData = Response.createSuccededResponse();

        // 3. Traducimos el mensaje de éxito usando el catálogo
        String translatedSuccessMessage = messageCatalogService.getMessageContent(MessagesEnum.SUCCESS_OPERATION);
        responseObjectData.addMessage(translatedSuccessMessage);

        return new ResponseEntity<>(responseObjectData, HttpStatus.CREATED);
    }
}