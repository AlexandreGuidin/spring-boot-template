package org.template.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class TemplateRequestedListener {
    private static final Logger logger = LoggerFactory.getLogger(TemplateRequestedListener.class);

    @JmsListener(destination = "template-template_requested")
    public void acceptMessage(@Header(JmsHeaders.MESSAGE_ID) String messageId, Integer content) {
        logger.info(String.format("received message with id %s and content %s", messageId, content));
    }
}
