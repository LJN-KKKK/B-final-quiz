package com.example.demo.exception;

public class PersonNotExistsException extends RuntimeException {
    public PersonNotExistsException(String message) {
        super(message);
    }
}