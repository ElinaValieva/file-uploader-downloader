package com.elina.exception;

public class BusinessLogicException extends Exception {

    private final String error;

    public String getError() {
        return error;
    }

    public BusinessLogicException(String error) {
        this.error = error;
    }
}