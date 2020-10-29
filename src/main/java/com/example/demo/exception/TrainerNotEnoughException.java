package com.example.demo.exception;

public class TrainerNotEnoughException extends RuntimeException {
    public TrainerNotEnoughException(String message) {
        super(message);
    }
}
