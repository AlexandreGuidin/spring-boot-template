package org.template.listeners;


import org.junit.jupiter.api.Test;
import org.test.BaseJmsListenerTest;

import java.util.Arrays;

public class TemplateRequestedListenerTest extends BaseJmsListenerTest {

    private final String queue = "template-template_requested";
    private final String dlq = "template-template_requested_dlq";

    @Test
    public void receive_messages() {
        sendMessages(queue, Arrays.asList(1, 2));

        assertQueueMessagesCount(queue, 0);
        assertQueueMessagesCount(dlq, 0);
    }

    @Test
    public void receive_messages_dlq() {
        sendMessages(queue, Arrays.asList("a", "b"));

        assertQueueMessagesCount(queue, 0);
        assertQueueMessagesCount(dlq, 2);
    }
}
