package co.edu.uco.aurora.infrastructure.controller;

import co.edu.uco.aurora.crosscutting.messagescatalog.MessageCatalogService;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.features.customer.findcustomer.application.inputport.FindCustomerInputPort;
import co.edu.uco.aurora.features.customer.findcustomer.application.inputport.dto.FindCustomerDTO;
import co.edu.uco.aurora.infrastructure.controller.dto.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class FindCustomerController {

    private final FindCustomerInputPort inputPort;
    private final MessageCatalogService messageCatalogService;

    // Inyectamos el servicio de mensajes aquí también
    public FindCustomerController(FindCustomerInputPort inputPort, MessageCatalogService messageCatalogService) {
        this.inputPort = inputPort;
        this.messageCatalogService = messageCatalogService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_OPERADOR')")
    public ResponseEntity<Response<FindCustomerDTO>> findAllCustomers() {

        // 1. Ejecutamos el caso de uso (sin try-catch)
        List<FindCustomerDTO> customers = inputPort.execute(null);

        // 2. Armamos la respuesta exitosa
        Response<FindCustomerDTO> responseObjectData = Response.createSuccededResponse();
        responseObjectData.setData(customers);

        // 3. ¡Traducimos el mensaje de éxito!
        String translatedSuccessMessage = messageCatalogService.getMessageContent(MessagesEnum.SUCCESS_OPERATION);
        responseObjectData.addMessage(translatedSuccessMessage);

        return new ResponseEntity<>(responseObjectData, HttpStatus.OK);
    }
}