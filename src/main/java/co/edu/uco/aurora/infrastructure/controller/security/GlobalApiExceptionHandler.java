package co.edu.uco.aurora.infrastructure.controller.security;

import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessageCatalogService;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.infrastructure.controller.dto.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public final class GlobalApiExceptionHandler {

    private final MessageCatalogService messageCatalogService;

    public GlobalApiExceptionHandler(MessageCatalogService messageCatalogService) {
        this.messageCatalogService = messageCatalogService;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<String>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request) {

        Response<String> responseObjectData = Response.createFailedResponse();
        String userMessage = messageCatalogService.getMessageContent(MessagesEnum.ERROR_HTTP_MESSAGE_NOT_READABLE_GENERIC);

        responseObjectData.addMessage(userMessage);
        return new ResponseEntity<>(responseObjectData, HttpStatus.BAD_REQUEST);
    }

    // AHORA SÍ: Captura todos los errores de Aurora que no sean atrapados en el Controller
    @ExceptionHandler(AuroraException.class)
    public ResponseEntity<Response<String>> handleAuroraException(
            AuroraException ex, WebRequest request) {

        Response<String> responseObjectData = Response.createFailedResponse();
        String translatedMessage;

        try {
            MessagesEnum enumKey = MessagesEnum.valueOf(ex.getUserMessage());
            // Aquí consulta a Strapi (o a la caché local) el mensaje en español/inglés
            translatedMessage = messageCatalogService.getMessageContent(enumKey);
        } catch (IllegalArgumentException e) {
            translatedMessage = ex.getUserMessage();
        }

        responseObjectData.addMessage(translatedMessage);
        return new ResponseEntity<>(responseObjectData, HttpStatus.BAD_REQUEST);
    }

    // NUEVO: Captura excepciones genéricas (Errores 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<String>> handleGenericException(
            Exception ex, WebRequest request) {

        ex.printStackTrace(); // Recomendable cambiar esto por un Logger real en producción (ej. slf4j)

        Response<String> responseObjectData = Response.createFailedResponse();
        String translatedMessage;

        try {
            // Traduce el mensaje genérico inesperado
            translatedMessage = messageCatalogService.getMessageContent(MessagesEnum.CUSTOMERS_UNEXPECTED_ERROR);
        } catch (Exception e) {
            translatedMessage = MessagesEnum.CUSTOMERS_UNEXPECTED_ERROR.name();
        }

        responseObjectData.addMessage(translatedMessage);
        return new ResponseEntity<>(responseObjectData, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}