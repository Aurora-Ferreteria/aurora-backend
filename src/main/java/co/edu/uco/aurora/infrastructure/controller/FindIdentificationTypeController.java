package co.edu.uco.aurora.infrastructure.controller;

import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.inputport.FindIdentificationTypeInputPort;
import co.edu.uco.aurora.features.identificationtype.findidentificationtype.application.inputport.dto.FindIdentificationTypeDTO;
import co.edu.uco.aurora.infrastructure.controller.dto.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/identification-types")
public class FindIdentificationTypeController {

    private final FindIdentificationTypeInputPort inputPort;

    public FindIdentificationTypeController(FindIdentificationTypeInputPort inputPort) {
        this.inputPort = inputPort;
    }

    @GetMapping
    public ResponseEntity<Response<FindIdentificationTypeDTO>> findAllIdentificationTypes() {

        Response<FindIdentificationTypeDTO> responseObjectData = Response.createSuccededResponse();
        HttpStatusCode responseStatusCode = HttpStatus.OK;

        try {
            List<FindIdentificationTypeDTO> types = inputPort.execute(null);
            responseObjectData.setData(types);
            responseObjectData.addMessage(
                    MessagesEnum.SUCCESS_OPERATION.name()
            );

        } catch (final AuroraException exception) {
            responseObjectData = Response.createFailedResponse();
            responseObjectData.addMessage(exception.getUserMessage());
            responseStatusCode = HttpStatus.BAD_REQUEST;
            exception.printStackTrace();

        } catch (final Exception exception) {
            responseObjectData = Response.createFailedResponse();
            responseObjectData.addMessage(
                    MessagesEnum.FIND_IDENTIFICATION_TYPE_ERROR.name()
            );
            responseStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            exception.printStackTrace();
        }

        return new ResponseEntity<>(responseObjectData, responseStatusCode);
    }
}