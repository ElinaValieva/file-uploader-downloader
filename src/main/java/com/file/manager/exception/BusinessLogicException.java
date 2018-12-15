package com.file.manager.exception;

public class BusinessLogicException extends Exception {

    private final String error;

    public String getError() {
        return error;
    }

    public BusinessLogicException(String error) {
        this.error = error;
    }
}