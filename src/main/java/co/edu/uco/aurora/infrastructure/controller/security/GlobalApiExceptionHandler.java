package co.edu.uco.aurora.infrastructure.controller.security;

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<String>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request) {

        Response<String> responseObjectData = Response.createFailedResponse();
        HttpStatus responseStatusCode = HttpStatus.BAD_REQUEST;

        // Se asigna el mensaje genérico para cualquier error de formato o lectura
        String userMessage = MessagesEnum.ERROR_HTTP_MESSAGE_NOT_READABLE_GENERIC.name();

        responseObjectData.addMessage(userMessage);

        ex.printStackTrace();

        return new ResponseEntity<>(responseObjectData, responseStatusCode);
    }
}