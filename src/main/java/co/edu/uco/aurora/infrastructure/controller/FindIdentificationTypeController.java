package co.edu.uco.aurora.infrastructure.controller;

import co.edu.uco.aurora.crosscutting.messagescatalog.MessageCatalogService;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.inputport.FindIdentificationTypeInputPort;
import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.inputport.dto.FindIdentificationTypeDTO;
import co.edu.uco.aurora.infrastructure.controller.dto.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/identification-types")
public class FindIdentificationTypeController {

    private final FindIdentificationTypeInputPort inputPort;
    private final MessageCatalogService messageCatalogService;

    // Inyectamos el servicio de mensajes
    public FindIdentificationTypeController(FindIdentificationTypeInputPort inputPort, MessageCatalogService messageCatalogService) {
        this.inputPort = inputPort;
        this.messageCatalogService = messageCatalogService;
    }

    @GetMapping
    public ResponseEntity<Response<FindIdentificationTypeDTO>> findAllIdentificationTypes() {

        // 1. Ejecutamos (sin try-catch)
        List<FindIdentificationTypeDTO> types = inputPort.execute(null);

        // 2. Armamos la respuesta
        Response<FindIdentificationTypeDTO> responseObjectData = Response.createSuccededResponse();
        responseObjectData.setData(types);

        // 3. Traducimos
        String translatedSuccessMessage = messageCatalogService.getMessageContent(MessagesEnum.SUCCESS_OPERATION);
        responseObjectData.addMessage(translatedSuccessMessage);

        return new ResponseEntity<>(responseObjectData, HttpStatus.OK);
    }
}