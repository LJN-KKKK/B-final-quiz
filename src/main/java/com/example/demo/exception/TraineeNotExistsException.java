package com.example.demo.exception;

public class TraineeNotExistsException extends RuntimeException {
    public TraineeNotExistsException(String message) {
        super(message);
    }
}