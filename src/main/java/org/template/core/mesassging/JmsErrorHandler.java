package org.template.core.mesassging;

import com.amazon.sqs.javamessaging.SQSQueueDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

import java.text.MessageFormat;
import java.util.Objects;

@Component
@ConditionalOnProperty(name = {"aws.sqs.local-endpoint"})
public class JmsErrorHandler implements ErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(JmsErrorHandler.class);

    @Value("${aws.sqs.max-tries}")
    private Integer maxTries;

    private void parseError(MessageConversionException ex) {
        try {
            MessageHeaders headers = Objects.requireNonNull(ex.getFailedMessage()).getHeaders();
            String payload = (String) ex.getFailedMessage().getPayload();

            JmsMessageError error = new JmsMessageError()
                    .setQueue(((SQSQueueDestination) Objects.requireNonNull(headers.get("jms_destination"))).getQueueName())
                    .setDeliveriesTries((Integer) headers.get("JMSXDeliveryCount"))
                    .setMessageId(Objects.requireNonNull(headers.getId()).toString())
                    .setMessage(payload);

            log.error(String.format("error while trying to parse message. data: %s", error));

            if (error.getDeliveriesTries() >= maxTries) {
                log.info(String.format("message with id %s will be sent to dlq %s_dlq", error.getMessageId(), error.getQueue()));
            }
        } catch (Exception e) {
            log.error(String.format("error while trying to parse message. original ex: %s. parse ex: %s",
                    ex.getFailedMessage(),
                    e.getMessage()));
        }
    }

    @Override
    public void handleError(Throwable t) {
        if (t.getCause() instanceof MessageConversionException) {
            MessageConversionException conversionException = (MessageConversionException) t.getCause();
            parseError(conversionException);
            return;
        }

        log.error(MessageFormat.format("unexpected error while processing message: {0}", t.getCause().getMessage()));
    }
}
