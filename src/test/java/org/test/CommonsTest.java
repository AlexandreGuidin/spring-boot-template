package org.test;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.QueueAttributeName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Map;

public abstract class CommonsTest {
    private static final Logger logger = LoggerFactory.getLogger(CommonsTest.class);

    protected Map<String, String> getQueueAttributes(AmazonSQS amazonSQS, String queue) {
        if (amazonSQS == null) return null;
        String queueUrl = amazonSQS.getQueueUrl(queue).getQueueUrl();

        GetQueueAttributesRequest attrRequest = new GetQueueAttributesRequest(queueUrl);
        attrRequest.setAttributeNames(Collections.singletonList(QueueAttributeName.All.name()));
        return amazonSQS.getQueueAttributes(attrRequest).getAttributes();
    }

    protected Integer countQueueMessages(AmazonSQS amazonSQS, String queue) {
        if (amazonSQS == null) return null;

        Map<String, String> attributes = getQueueAttributes(amazonSQS, queue);
        String count = attributes.get(QueueAttributeName.ApproximateNumberOfMessages.name());
        return Integer.parseInt(count);
    }

    protected byte[] readFile(String filePath) {
        try {
            File file = ResourceUtils.getFile("classpath:" + filePath);
            return Files.readAllBytes(file.toPath());
        } catch (Exception e) {
            logger.error("Error loading file", e);
            throw new RuntimeException(e);
        }
    }

    protected String readJson(String filePath) {
        return new String(readFile(filePath));
    }

    protected MockMultipartFile readCsv(String filePath) {
        return new MockMultipartFile("file", "test.csv", "text/csv", readFile(filePath));
    }
}
