package com.example.springbackendv2.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String email){
        super("Email: " + email + " already exists");
    }

}
