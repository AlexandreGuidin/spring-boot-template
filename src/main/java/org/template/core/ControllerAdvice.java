package org.template.core;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.template.core.exception.ApiException;
import org.template.core.exception.NotFoundException;
import org.template.core.exception.ValidationException;
import org.template.core.model.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public List<ValidationError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) ex.getCause();
            String fieldPath = invalidFormatException.getPath()
                    .stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("."));
            return Collections.singletonList(new ValidationError(fieldPath, "invalid"));
        }
        logger.error("handleHttpMessageNotReadableException", ex);
        return Collections.singletonList(new ValidationError("request", "invalid"));
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ValidationError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ValidationError> errors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> new ValidationError(field.getField(),
                        field.getDefaultMessage(),
                        Optional.ofNullable(field.getRejectedValue()).map(Object::toString).orElse("")))
                .collect(Collectors.toList());

        String errorsStr = errors.stream().map(ValidationError::toString).collect(Collectors.joining(";"));
        logger.error("handleMethodArgumentNotValidException errors: " + errorsStr);
        return errors;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ValidationException.class)
    public List<ValidationError> handleValidationException(ValidationException ex) {
        String errorsStr = ex.getValidationErrors().stream().map(ValidationError::toString).collect(Collectors.joining(";"));
        logger.error("handleValidationException errors: " + errorsStr);
        return ex.getValidationErrors();
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException ex) {
        logger.error("handleApiException", ex);
        return ResponseEntity.status(ex.getStatus()).build();
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public List<ValidationError> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        logger.error("handleMissingServletRequestParameterException fields with error: " + ex.getMessage());
        return Collections.singletonList(new ValidationError(ex.getParameterName(), "invalid"));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex) {
        logger.error("handleException", ex);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public List<ValidationError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        logger.error("handleMethodArgumentTypeMismatchException", ex);
        return Collections.singletonList(new ValidationError(ex.getName(), "invalid"));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public void handleNotFoundException(NotFoundException ex) {
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<ValidationError> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.error("handleConstraintViolationException ", ex);
        return ex.getConstraintViolations()
                .stream()
                .map(violation -> new ValidationError(
                        Arrays.asList(violation.getPropertyPath().toString().split("\\.")).get(1),
                        violation.getMessage()))
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public void handleBadCredentialsException(BadCredentialsException ex) {
        logger.error("handleBadCredentialsException " + ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public void handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        logger.error("handleMissingRequestHeaderException " + ex.getMessage());
    }
}
