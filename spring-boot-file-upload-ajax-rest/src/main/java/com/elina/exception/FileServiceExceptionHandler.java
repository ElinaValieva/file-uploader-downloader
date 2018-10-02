package com.elina.exception;

import com.elina.controller.RestFileServiceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.ParseException;

@ControllerAdvice
public class FileServiceExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(FileServiceExceptionHandler.class);

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<?> handleBusinessLogicException(BusinessLogicException ex) {
        logger.error(ex.getError());
        ex.printStackTrace();
        return ResponseEntity.badRequest().body(ex.getError());
    }

    @ExceptionHandler({IOException.class, ParseException.class, UnsupportedEncodingException.class, MalformedURLException.class})
    public ResponseEntity<?> handleIOException(Exception ex) {
        logger.error(ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
