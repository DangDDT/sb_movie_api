package com.example.movie.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.context.request.WebRequest;

import com.example.movie.utils.response_handler.ResponseHandler;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = InternalServerError.class)
    public ResponseEntity<Object> handleInternalServerErrorException(Exception ex, WebRequest request) {
        final ErrorMessage errorMessage = new ErrorMessage(HttpStatus.valueOf(500), ex.getLocalizedMessage(), null);
        return ResponseHandler.generateResponse(errorMessage.getMessage(), errorMessage.getStatusCode(),
                errorMessage.getData());
    }

    @ExceptionHandler(value = { HttpMessageNotReadableException.class, BadRequest.class,
            IllegalArgumentException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(Exception ex, WebRequest request) {
        final ErrorMessage errorMessage = new ErrorMessage(HttpStatus.valueOf(400), ex.getLocalizedMessage(), null);
        return ResponseHandler.generateResponse(errorMessage.getMessage(), errorMessage.getStatusCode(),
                errorMessage.getData());
    }
}
