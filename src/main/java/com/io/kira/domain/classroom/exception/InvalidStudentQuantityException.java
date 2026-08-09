package com.io.kira.domain.classroom.exception;

public final class InvalidStudentQuantityException extends RuntimeException {
    public InvalidStudentQuantityException(String message) {
        super(message);
    }
}