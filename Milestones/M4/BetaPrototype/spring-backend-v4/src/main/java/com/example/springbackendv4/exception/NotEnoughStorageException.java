package com.example.springbackendv2.exception;

public class NotEnoughStorageException extends RuntimeException{
    public NotEnoughStorageException(){
        super("Not enough storage ");
    }
}
