package co.edu.uco.aurora.infrastructure.controller.security;

import co.edu.uco.aurora.crosscutting.exception.AuroraException;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessageCatalogService;
import co.edu.uco.aurora.crosscutting.messagescatalog.MessagesEnum;
import co.edu.uco.aurora.infrastructure.controller.dto.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public final class GlobalApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalApiExceptionHandler.class);
    private final MessageCatalogService messageCatalogService;

    public GlobalApiExceptionHandler(MessageCatalogService messageCatalogService) {
        this.messageCatalogService = messageCatalogService;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<String>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request) {

        String headerCrudo = request.getHeader("Accept-Language");
        String idiomaDetectado = org.springframework.context.i18n.LocaleContextHolder.getLocale().getLanguage();

        log.info("--- DEBUGG IDIOMAS ---");
        log.info("1. Lo que envió Vue (Header crudo): {}", headerCrudo);
        log.info("2. Lo que Spring entendió: {}", idiomaDetectado);
        log.info("----------------------");

        Response<String> responseObjectData = Response.createFailedResponse();
        String userMessage = messageCatalogService.getMessageContent(MessagesEnum.ERROR_HTTP_MESSAGE_NOT_READABLE_GENERIC);

        responseObjectData.addMessage(userMessage);
        return new ResponseEntity<>(responseObjectData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuroraException.class)
    public ResponseEntity<Response<String>> handleAuroraException(
            AuroraException ex, WebRequest request) {

        Response<String> responseObjectData = Response.createFailedResponse();
        String translatedMessage;

        try {
            MessagesEnum enumKey = MessagesEnum.valueOf(ex.getUserMessage());
            translatedMessage = messageCatalogService.getMessageContent(enumKey);
        } catch (IllegalArgumentException _) {
            translatedMessage = ex.getUserMessage();
        }

        responseObjectData.addMessage(translatedMessage);
        return new ResponseEntity<>(responseObjectData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<String>> handleGenericException(
            Exception ex, WebRequest request) {

        log.error("Se ha presentado un error inesperado: ", ex);

        Response<String> responseObjectData = Response.createFailedResponse();
        String translatedMessage;

        try {
            translatedMessage = messageCatalogService.getMessageContent(MessagesEnum.CUSTOMERS_UNEXPECTED_ERROR);
        } catch (Exception _) {
            translatedMessage = MessagesEnum.CUSTOMERS_UNEXPECTED_ERROR.name();
        }

        responseObjectData.addMessage(translatedMessage);
        return new ResponseEntity<>(responseObjectData, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}