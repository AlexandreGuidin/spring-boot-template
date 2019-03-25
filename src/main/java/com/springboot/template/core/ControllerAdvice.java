package com.springboot.template.core;

import com.springboot.template.core.exception.ApiException;
import com.springboot.template.core.exception.ValidationException;
import com.springboot.template.model.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ValidationError> handleValidationException(MethodArgumentNotValidException exception) {
        return exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> new ValidationError(field.getField(), field.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ValidationException.class)
    public List<ValidationError> handleValidationException(ValidationException ex) {
        return ex.getValidationErrors();
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity apiException(ApiException ex) {
        return ResponseEntity.status(ex.getStatus()).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void httpMessageNotReadable(HttpMessageNotReadableException ex) {
        logger.error("ControllerAdvice.httpMessageNotReadable ", ex);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public List<ValidationError> handleMissingParams(MissingServletRequestParameterException ex) {
        return Collections.singletonList(new ValidationError(ex.getParameterName(), "invalid"));
    }
}
