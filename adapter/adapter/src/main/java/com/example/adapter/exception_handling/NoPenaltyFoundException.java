package com.example.adapter.exception_handling;

public class NoPenaltyFoundException extends RuntimeException{
    public NoPenaltyFoundException(String message) {
        super(message);
    }
}
