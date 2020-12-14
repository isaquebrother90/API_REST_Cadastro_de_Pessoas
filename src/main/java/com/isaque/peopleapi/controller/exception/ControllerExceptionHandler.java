package com.isaque.peopleapi.controller.exception;

import com.isaque.peopleapi.service.exception.BusinessException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> MethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), request.getRequestURI(), "MethodArgumentNotValidException");

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            err.getErrors().put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> AlreadyEmailException(BusinessException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.valueOf(e.getCode());
        StandardError err = new StandardError(Instant.now(), status.value(), request.getRequestURI(), "BusinessException");

        err.getErrors().put(e.getField(), e.getError());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> ConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), request.getRequestURI(), "ConstraintViolationException");

        for (ConstraintViolation<?> fieldError : e.getConstraintViolations()) {
            int lastIndex = fieldError.getPropertyPath().toString().lastIndexOf(".") + 1;
            lastIndex = lastIndex == -1 ? 0 : lastIndex;
            String propertyField = fieldError.getPropertyPath().toString().substring(lastIndex);
            err.getErrors().put(propertyField, fieldError.getMessage());
        }
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), request.getRequestURI(), "MethodArgumentTypeMismatchException");

        err.getErrors().put(e.getName(), e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<StandardError> InvalidFormatException(InvalidFormatException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), request.getRequestURI(), "InvalidFormatException");

        err.getErrors().put(e.getPath().get(0).getFieldName(), e.getOriginalMessage());
        return ResponseEntity.status(status).body(err);
    }
}
