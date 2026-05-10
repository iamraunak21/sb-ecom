package com.example.sb_ecom.ecom.exception;

import com.example.sb_ecom.ecom.payload.APIRespone;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach((err)->{
            String fieldName = err.getField();
            String message = err.getDefaultMessage();
            response.put(fieldName, message);
        });


        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST) ;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIRespone> myMethodResourceNotFoundException(ResourceNotFoundException e){
        String message = e.getMessage();
        APIRespone apiResponse = new APIRespone();
        apiResponse.setMessage(message);
        apiResponse.setStatus(false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND) ;

    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIRespone> myAPIException(APIException e){
        String message = e.getMessage();
        APIRespone apiResponse = new APIRespone();
        apiResponse.setMessage(message);
        apiResponse.setStatus(false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND) ;

    }

}
