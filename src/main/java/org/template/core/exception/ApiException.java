package org.template.core.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private final Integer status;
    private Boolean logError = true;

    public ApiException() {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ApiException(Boolean logError) {
        this.logError = logError;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ApiException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ApiException(String message, Boolean logError) {
        super(message);
        this.logError = logError;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ApiException(HttpStatus status) {
        this.status = status.value();
    }

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status.value();
    }

    public ApiException(String message, Throwable throwable) {
        super(message, throwable);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ApiException(Throwable throwable) {
        super(throwable);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ApiException(Throwable throwable, HttpStatus status) {
        super(throwable);
        this.status = status.value();
    }

    public ApiException(Throwable throwable, HttpStatus status, String message) {
        super(message, throwable);
        this.status = status.value();
    }

    public Integer getStatus() {
        return status;
    }

    public Boolean logError() {
        return logError;
    }
}
