package org.template.core.mesassging;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.jms.Session;

@Configuration
@EnableJms
@ConditionalOnProperty(name = {"aws.region", "aws.sqs.local-endpoint"})
public class JmsConfig {

    @Value("${app.environment}")
    private String environment;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.sqs.local-endpoint}")
    private String localEndpoint;

    public AmazonSQS sqsConfig() {
        if (environment.equals("local")) {
            return AmazonSQSClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("any", "any")))
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(localEndpoint, awsRegion))
                    .build();
        }

        return AmazonSQSClientBuilder
                .standard()
                .withRegion(awsRegion)
                .withCredentials(InstanceProfileCredentialsProvider.createAsyncRefreshingProvider(true))
                .build();
    }

    @Bean
    public SQSConnectionFactory sqsConnectionFactory() {
        return new SQSConnectionFactory(new ProviderConfiguration(), sqsConfig());
    }


    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter mappingJackson2MessageConverter = new MappingJackson2MessageConverter();
        mappingJackson2MessageConverter.setObjectMapper(objectMapper);
        mappingJackson2MessageConverter.setTargetType(MessageType.TEXT);
        mappingJackson2MessageConverter.setTypeIdPropertyName("_type");
        return mappingJackson2MessageConverter;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(JmsErrorHandler errorHandler,
            MessageConverter messageConverter,
            SQSConnectionFactory sqsConnectionFactory) {

        CustomJmsListenerContainerFactory factory = new CustomJmsListenerContainerFactory();
        factory.setConnectionFactory(sqsConnectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setErrorHandler(errorHandler);

        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);

        return factory;
    }

    @Bean
    public JmsTemplate defaultJmsTemplate(MessageConverter messageConverter, SQSConnectionFactory sqsConnectionFactory) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(sqsConnectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

}
