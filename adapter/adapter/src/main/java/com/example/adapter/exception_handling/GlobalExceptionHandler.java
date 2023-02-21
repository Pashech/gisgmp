package com.example.adapter.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(NoPenaltyFoundException exception){
        IncorrectData incorrectData = new IncorrectData();
        incorrectData.setInfo(exception.getMessage());
        incorrectData.setStatus(400);
        return new ResponseEntity<>(incorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(HttpClientErrorException exception){
        InformationException informationException = new InformationException(exception.getMessage());
        return new ResponseEntity<>(informationException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleServerException(HttpServerErrorException exception){
        ServerErrorException serverErrorException = new ServerErrorException(exception.getMessage());
        return new ResponseEntity<>(serverErrorException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Map<String, String>> handleInfoRequestException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<IncorrectData> handleConstraintViolationException(ConstraintViolationException e) {
        IncorrectData incorrectData = new IncorrectData();
        incorrectData.setInfo("not valid due to validation error: " + e.getMessage());
        incorrectData.setStatus(400);
        return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
    }
}
