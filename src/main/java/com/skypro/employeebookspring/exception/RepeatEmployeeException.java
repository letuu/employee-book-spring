package com.skypro.employeebookspring.exception;

public class RepeatEmployeeException extends RuntimeException {
    public RepeatEmployeeException(String message) {
        super(message);
    }
}
