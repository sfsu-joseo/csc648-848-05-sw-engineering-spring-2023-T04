package com.example.springbackendv4.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String username){
        super("Username: " + username + " already exists");
    }

}
