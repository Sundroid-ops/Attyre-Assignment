package com.example.Attyre.Assignment.Advice;

import com.example.Attyre.Assignment.Exception.InternalServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestResponseExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestResponseExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> metMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String, String> err = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
            err.put(error.getField(), error.getDefaultMessage()));
        logger.info("Invalid Incoming Requests : {}", exception);
        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> internalServiceException(InternalServerException exception){
        logger.warn("Error occurred : {}", exception.getMessage());
        ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        return ResponseEntity.internalServerError().body(error);
    }
}