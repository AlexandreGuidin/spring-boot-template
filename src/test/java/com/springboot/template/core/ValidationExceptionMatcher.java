package com.springboot.template.core;

import com.springboot.template.core.exception.ValidationException;
import com.springboot.template.model.ValidationError;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collections;
import java.util.List;

public class ValidationExceptionMatcher extends TypeSafeMatcher<ValidationException> {

    private List<ValidationError> throwed;
    private List<ValidationError> expected;

    private ValidationExceptionMatcher(List<ValidationError> expected) {
        this.expected = expected;
    }

    public static ValidationExceptionMatcher assertError(String key, String message) {
        return new ValidationExceptionMatcher(Collections.singletonList(new ValidationError(key, message)));
    }

    public static ValidationExceptionMatcher assertError(ValidationError error) {
        return new ValidationExceptionMatcher(Collections.singletonList(error));
    }

    public static ValidationExceptionMatcher assertError(List<ValidationError> errors) {
        return new ValidationExceptionMatcher(errors);
    }

    @Override
    protected boolean matchesSafely(ValidationException exception) {
        throwed = exception.getValidationErrors();
        return throwed.equals(expected);
    }

    @Override
    public void describeTo(Description desc) {
        desc.appendText("Throwed =").appendValue(throwed).appendText(" Expected =").appendValue(expected);

    }
}
