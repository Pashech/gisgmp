package com.example.smev_gisgmp.exception_handling;

import java.util.function.Supplier;

public class InformationRequestException extends RuntimeException {
    public InformationRequestException(String message) {
        super(message);
    }
}
