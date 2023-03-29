package com.example.springbackendv2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UserNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> exceptionHandler(UserNotFoundException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", exception.getMessage());

        return errorMap;
    }

    @ResponseBody
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> exceptionHandler(InvalidCredentialsException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", exception.getMessage());
        return errorMap;
    }

    @ResponseBody
    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> exceptionHandler(EmailAlreadyExistsException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", exception.getMessage());
        return errorMap;
    }

    @ResponseBody
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> exceptionHandler(UsernameAlreadyExistsException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", exception.getMessage());
        return errorMap;
    }
}
