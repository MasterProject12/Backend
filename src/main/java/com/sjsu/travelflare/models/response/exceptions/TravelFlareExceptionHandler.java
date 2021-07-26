package com.sjsu.travelflare.models.response.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class TravelFlareExceptionHandler {

    @ExceptionHandler(value = {ParameterMissingException.class, IncorrectFormatException.class})
    public ResponseEntity<Object> handleMissingParameterException(Exception ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), new Date());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), new Date());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
