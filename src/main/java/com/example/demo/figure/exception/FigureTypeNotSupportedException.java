package com.example.demo.figure.exception;

public class FigureTypeNotSupportedException extends RuntimeException {
    public FigureTypeNotSupportedException(String message) {
        super(message);
    }
}
