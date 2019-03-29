package com.springboot.template.core.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private Integer status;

    public ApiException() {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ApiException(HttpStatus status) {
        this.status = status.value();
    }

    public ApiException(Integer status) {
        this.status = status;
    }

    public ApiException(Throwable throwable) {
        super(throwable);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ApiException(Throwable throwable, Integer status) {
        super(throwable);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
