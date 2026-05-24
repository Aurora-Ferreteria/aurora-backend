package co.edu.uco.aurora.infrastructure.controller;

import co.edu.uco.aurora.crosscutting.messagescatalog.MessageCatalogService;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.features.customer.findcustomer.application.inputport.FindCustomerInputPort;
import co.edu.uco.aurora.features.customer.findcustomer.application.inputport.dto.FindCustomerDTO;
import co.edu.uco.aurora.infrastructure.controller.dto.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customers (Clientes)", description = "Operaciones relacionadas con la creación y gestión de clientes")
public class FindCustomerController {

    private final FindCustomerInputPort inputPort;
    private final MessageCatalogService messageCatalogService;

    public FindCustomerController(FindCustomerInputPort inputPort, MessageCatalogService messageCatalogService) {
        this.inputPort = inputPort;
        this.messageCatalogService = messageCatalogService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_OPERADOR')")
    @Operation(
            summary = "Obtener todos los clientes",
            description = "Retorna una lista con todos los clientes registrados en el sistema. Requiere permisos de ADMIN, VENDEDOR u OPERADOR.",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado (Falta token JWT o es inválido)"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado (No tiene los roles requeridos)"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Response<FindCustomerDTO>> findAllCustomers() {

        List<FindCustomerDTO> customers = inputPort.execute(null);

        Response<FindCustomerDTO> responseObjectData = Response.createSuccededResponse();
        responseObjectData.setData(customers);

        String translatedSuccessMessage = messageCatalogService.getMessageContent(MessagesEnum.SUCCESS_OPERATION);
        responseObjectData.addMessage(translatedSuccessMessage);

        return new ResponseEntity<>(responseObjectData, HttpStatus.OK);
    }
}