package com.example.adapter.exception_handling;

public class ServerErrorException extends RuntimeException{
    public ServerErrorException(String message) {
        super(message);
    }
}
