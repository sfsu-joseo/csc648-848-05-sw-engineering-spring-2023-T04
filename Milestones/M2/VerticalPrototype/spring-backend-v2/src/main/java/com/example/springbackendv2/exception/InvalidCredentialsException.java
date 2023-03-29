package com.example.springbackendv2.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(){
        super("Invalid Credentials");
    }

}
