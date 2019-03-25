package com.springboot.template.core.exception;

import com.springboot.template.model.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends RuntimeException {

    private List<ValidationError> validationErrors;

    private ValidationException(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {
        private List<ValidationError> errors;

        private Builder() {
            errors = new ArrayList<>();
        }

        public ValidationException build() {
            return new ValidationException(errors);
        }

        public Builder addError(String message, String field) {
            this.errors.add(new ValidationError().setMessage(message).setField(field));
            return this;
        }

        public Builder addError(ValidationError error) {
            this.errors.add(error);
            return this;
        }

        public Builder addErrors(List<ValidationError> errors) {
            this.errors = errors;
            return this;
        }
    }
}
