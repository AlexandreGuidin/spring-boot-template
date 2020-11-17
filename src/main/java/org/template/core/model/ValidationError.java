package org.template.core.model;

public class ValidationError {

    private String field;
    private String message;
    private String value;

    public boolean haveValues() {
        return !field.isEmpty() && !message.isEmpty();
    }

    public ValidationError() {
    }

    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public ValidationError(String field, String message, String value) {
        this.field = field;
        this.message = message;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public ValidationError setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "ValidationError{" +
                "field='" + field + '\'' +
                ", message='" + message + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
