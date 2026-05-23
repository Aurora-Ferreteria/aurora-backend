package co.edu.uco.aurora.infrastructure.controller.security;

import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessageCatalogService;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.infrastructure.controller.dto.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public final class GlobalApiExceptionHandler {

    private final MessageCatalogService messageCatalogService;

    public GlobalApiExceptionHandler(MessageCatalogService messageCatalogService) {
        this.messageCatalogService = messageCatalogService;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<String>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request) {

        Response<String> responseObjectData = Response.createFailedResponse();
        HttpStatus responseStatusCode = HttpStatus.BAD_REQUEST;

        String userMessage = messageCatalogService.getMessageContent(MessagesEnum.ERROR_HTTP_MESSAGE_NOT_READABLE_GENERIC);

        responseObjectData.addMessage(userMessage);
        ex.printStackTrace();

        return new ResponseEntity<>(responseObjectData, responseStatusCode);
    }

    @ExceptionHandler(AuroraException.class)
    public ResponseEntity<Response<String>> handleAuroraException(
            AuroraException ex, WebRequest request) {

        Response<String> responseObjectData = Response.createFailedResponse();
        HttpStatus responseStatusCode = HttpStatus.BAD_REQUEST;

        String translatedMessage;

        try {
            MessagesEnum enumKey = MessagesEnum.valueOf(ex.getUserMessage());

            translatedMessage = messageCatalogService.getMessageContent(enumKey);

        } catch (IllegalArgumentException e) {
            translatedMessage = ex.getUserMessage();
        }

        responseObjectData.addMessage(translatedMessage);

        return new ResponseEntity<>(responseObjectData, responseStatusCode);
    }
}