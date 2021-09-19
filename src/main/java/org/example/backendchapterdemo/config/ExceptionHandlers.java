package org.example.backendchapterdemo.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Map<String, Object>> validationExceptionHandler(ConstraintViolationException exception) {
        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonMap("Error", exception
                        .getConstraintViolations().stream()
                        .map(violation -> "Property " + violation.getPropertyPath() + " " + violation.getMessage())));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> requestExceptionHandler() {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonMap("Error", "Please verify your request parameters."));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> dataIntegrityExceptionHandler(DataIntegrityViolationException e) {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonMap("Error", "The data entered violates data integrity checks."));
    }
}
