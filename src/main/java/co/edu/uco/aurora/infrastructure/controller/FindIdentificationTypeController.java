package co.edu.uco.aurora.infrastructure.controller;

import co.edu.uco.aurora.crosscutting.messagescatalog.MessageCatalogService;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.inputport.FindIdentificationTypeInputPort;
import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.inputport.dto.FindIdentificationTypeDTO;
import co.edu.uco.aurora.infrastructure.controller.dto.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/identification-types")
@Tag(name = "Identification Types (Tipos de Identificación)", description = "Operaciones de consulta pública para los tipos de documentos")
public class FindIdentificationTypeController {

    private final FindIdentificationTypeInputPort inputPort;
    private final MessageCatalogService messageCatalogService;

    public FindIdentificationTypeController(FindIdentificationTypeInputPort inputPort, MessageCatalogService messageCatalogService) {
        this.inputPort = inputPort;
        this.messageCatalogService = messageCatalogService;
    }

    @GetMapping
    @Operation(
            summary = "Obtener todos los tipos de identificación",
            description = "Retorna una lista con los tipos de identificación disponibles en el sistema. Este endpoint es de acceso PÚBLICO."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tipos de identificación obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Response<FindIdentificationTypeDTO>> findAllIdentificationTypes() {

        List<FindIdentificationTypeDTO> types = inputPort.execute(null);

        Response<FindIdentificationTypeDTO> responseObjectData = Response.createSuccededResponse();
        responseObjectData.setData(types);

        String translatedSuccessMessage = messageCatalogService.getMessageContent(MessagesEnum.SUCCESS_OPERATION);
        responseObjectData.addMessage(translatedSuccessMessage);

        return new ResponseEntity<>(responseObjectData, HttpStatus.OK);
    }
}