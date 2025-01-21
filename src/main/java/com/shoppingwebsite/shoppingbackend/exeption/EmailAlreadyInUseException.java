package com.shoppingwebsite.shoppingbackend.exeption;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}