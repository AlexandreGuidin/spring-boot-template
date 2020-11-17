package org.template.listeners;


import org.junit.jupiter.api.Test;
import org.test.BaseJmsListenerTest;

import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

public class TemplateRequestedListenerTest extends BaseJmsListenerTest {

    private final String queue = "template-template_requested";
    private final String dlq = "template-template_requested_dlq";

    @Test
    public void receive_messages() throws InterruptedException {
        sendMessages(queue, Arrays.asList(1,2));

        sleep(200);
        assertThat(countQueueMessages(queue)).isEqualTo(0);
        assertThat(countQueueMessages(dlq)).isEqualTo(0);
    }

    @Test
    public void receive_messages_dlq() throws InterruptedException {
        sendMessages(queue, Arrays.asList("a", "b"));
        sleep(2000);

        assertThat(countQueueMessages(queue)).isEqualTo(0);
        assertThat(countQueueMessages(dlq)).isEqualTo(2);
    }
}
