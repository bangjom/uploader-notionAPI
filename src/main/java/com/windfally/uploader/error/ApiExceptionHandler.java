package com.windfally.uploader.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundException(UsernameNotFoundException e) {
        log.error("usernameNotFoundException", e);
        final ErrorResponse response = ErrorResponse.of(GlobalErrorCode.NOT_EXIST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleNotUniqueException(SQLIntegrityConstraintViolationException e) {
        log.error("SQLIntegrityConstraintViolationException", e);
        final ErrorResponse response = ErrorResponse.of(GlobalErrorCode.NOT_UNIQUE, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<ErrorResponse> handleNotUniqueException(IOException e) {
        log.error("IOException", e);
        final ErrorResponse response = ErrorResponse.of(GlobalErrorCode.IO_NOT_WORK, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorResponse> handleNotUniqueException(NullPointerException e) {
        log.error("NullPointerException", e);
        final ErrorResponse response = ErrorResponse.of(GlobalErrorCode.NOT_EXIST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistException.class)
    protected ResponseEntity<ErrorResponse> handleNotExistException(NotExistException e) {
        log.error("NotExistException", e);
        final ErrorResponse response = ErrorResponse.of(GlobalErrorCode.NOT_EXIST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotDeleteException.class)
    protected ResponseEntity<ErrorResponse> handleNotDeleteException(NotDeleteException e) {
        log.error("NotDeleteException", e);
        final ErrorResponse response = ErrorResponse.of(GlobalErrorCode.NOT_DELETE_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
