package com.example.springbackendv4.exception;

public class NotEnoughStorageException extends RuntimeException{
    public NotEnoughStorageException(){
        super("Not enough storage ");
    }
}
