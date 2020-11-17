package org.template.core.log;

import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorLogLayout {

    private final String className;
    private final String errorMessage;
    private final List<String> stack;

    public ErrorLogLayout(IThrowableProxy errorProxy) {
        this.className = errorProxy.getClassName();
        this.errorMessage = errorProxy.getMessage();
        this.stack = Arrays.stream(errorProxy.getStackTraceElementProxyArray())
                .map(StackTraceElementProxy::getSTEAsString)
                .collect(Collectors.toList());
    }

    public String getClassName() {
        return className;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<String> getStack() {
        return stack;
    }
}
