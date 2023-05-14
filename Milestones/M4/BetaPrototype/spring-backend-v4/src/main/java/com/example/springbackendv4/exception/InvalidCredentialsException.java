package com.example.springbackendv4.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(){
        super("Invalid Credentials");
    }

}
