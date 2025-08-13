package com.G19.hospital.exceptions;

import com.G19.hospital.exceptions.security.CustomSecurityException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ApiError apiError = new ApiError.Builder()
                .withMessage("Validation failed: " + errors)
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withCreatedAt()
                .build();
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityException(Exception ex){
        ApiError apiError = new ApiError.
                Builder()
                .withMessage("Validation Error")
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withCreatedAt()
                .build();

        return new ResponseEntity<>(apiError,apiError.getHttpStatus());

    }
    @ExceptionHandler(CustomSecurityException.class)
    public ResponseEntity<Object> handleCustomSecurityException(CustomSecurityException ex){
        ApiError apiError = new ApiError.
                Builder()
                .withMessage(ex.getMessage())
                .withHttpStatus(ex.getHttpStatus())
                .withCreatedAt()
                .build();

        return new ResponseEntity<>(apiError,apiError.getHttpStatus());

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception){

        logger.error(exception.getMessage(),exception);

        ApiError apiError = new ApiError.
                Builder()
                .withMessage("Some Error Occurred")
                .withHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .withCreatedAt()
                .build();

        return new ResponseEntity<>(apiError,apiError.getHttpStatus());

    }
}
