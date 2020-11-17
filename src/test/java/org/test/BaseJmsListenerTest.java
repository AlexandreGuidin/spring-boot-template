package org.test;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.PurgeQueueRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public abstract class BaseJmsListenerTest extends CommonsTest {

    @Autowired
    protected JmsTemplate jmsTemplate;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.sqs.local-endpoint}")
    private String localEndpoint;

    private AmazonSQS amazonSQS;

    public AmazonSQS getAmazonSQS() {
        if (amazonSQS == null) {
            amazonSQS = AmazonSQSClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("test", "test")))
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(localEndpoint, awsRegion))
                    .build();
        }

        return amazonSQS;
    }

    protected Integer countQueueMessages(String queue) {
        return super.countQueueMessages(getAmazonSQS(), queue);
    }

    @BeforeEach
    public void beforeEach() {
        getAmazonSQS().listQueues()
                .getQueueUrls()
                .forEach(q -> amazonSQS.purgeQueue(new PurgeQueueRequest(q)));
    }

    @AfterEach
    public void afterEach() {

    }

    protected void sendMessages(String queue, Class<?> message) {
        jmsTemplate.convertAndSend(queue, message);
    }

    protected void sendMessages(String queue, List<?> messages) {
        messages.forEach(m -> jmsTemplate.convertAndSend(queue, m));
    }

    protected void sleep(Integer millis) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(millis);
    }
}
