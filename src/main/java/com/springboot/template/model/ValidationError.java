package com.springboot.template.model;

import org.apache.commons.lang3.StringUtils;

public class ValidationError {

    private String field;
    private String message;

    public boolean haveValues() {
        return StringUtils.isNotEmpty(field) && StringUtils.isNotEmpty(message);
    }

    public ValidationError() {
    }

    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public ValidationError setField(String field) {
        this.field = field;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ValidationError setMessage(String message) {
        this.message = message;
        return this;
    }
}
