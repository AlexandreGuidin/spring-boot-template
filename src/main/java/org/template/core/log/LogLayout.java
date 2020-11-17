package org.template.core.log;

public class LogLayout {

    private String level;
    private String at;
    private String className;
    private String message;
    private ErrorLogLayout error;

    public String getLevel() {
        return level;
    }

    public LogLayout setLevel(String level) {
        this.level = level;
        return this;
    }

    public String getAt() {
        return at;
    }

    public LogLayout setAt(String at) {
        this.at = at;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public LogLayout setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public LogLayout setMessage(String message) {
        this.message = message;
        return this;
    }

    public ErrorLogLayout getError() {
        return error;
    }

    public LogLayout setError(ErrorLogLayout error) {
        this.error = error;
        return this;
    }
}
