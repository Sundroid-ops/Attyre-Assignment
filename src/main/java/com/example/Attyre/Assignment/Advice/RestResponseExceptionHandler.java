package com.example.Attyre.Assignment.Advice;

import com.example.Attyre.Assignment.Exception.InternalServerException;
import com.example.Attyre.Assignment.Exception.ProductNotFoundException;
import com.example.Attyre.Assignment.Exception.UserNotFoundException;
import com.example.Attyre.Assignment.Exception.UserPreferenceNotRegisteredException;
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
        logger.info("Invalid Incoming Requests : {}", exception.getMessage());
        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler({UserNotFoundException.class, ProductNotFoundException.class})
    public ResponseEntity<ErrorMessage> NotFoundException(Exception exception){
        logger.info("NOT FOUND: {}", exception.getMessage());
        ErrorMessage error = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserPreferenceNotRegisteredException.class)
    public ResponseEntity<String> UserPreferenceNotRegistered(UserPreferenceNotRegisteredException exception){
        logger.info("Preference not setup by User ID: {}", exception.getMessage());
        return ResponseEntity.ok().body("Preference Not Setup");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> internalServiceException(InternalServerException exception){
        logger.warn("Error occurred : {}", exception.getMessage());
        ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        return ResponseEntity.internalServerError().body(error);
    }
}
