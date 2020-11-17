package org.template.core.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.template.core.beans.JacksonBean;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class LogJsonAppender extends LayoutBase<ILoggingEvent> {

    private final ObjectMapper objectMapper;

    public LogJsonAppender() {
        this.objectMapper = new JacksonBean().snakeCaseJackson();
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        try {
            ErrorLogLayout error = Optional.ofNullable(event.getThrowableProxy())
                    .map(ErrorLogLayout::new)
                    .orElse(null);

            LogLayout log = new LogLayout()
                    .setAt(ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                    .setLevel(event.getLevel().levelStr)
                    .setClassName(event.getLoggerName())
                    .setMessage(event.getFormattedMessage())
                    .setError(error);

            return objectMapper.writeValueAsString(log) + "\n";
        } catch (Exception ex) {
            return "error parsing log event. Log: [" + event.toString() + "] Error: [" + ex.getMessage() + "] \n";
        }
    }
}
