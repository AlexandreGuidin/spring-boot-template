package org.test;


import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.PurgeQueueRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public abstract class BaseControllerTest extends CommonsTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    protected MockMvc mvc;

    @Autowired(required = false)
    protected List<JpaRepository<?, ?>> repositoryList;

    @Autowired(required = false)
    protected AmazonSQS amazonSQS;

    @BeforeEach
    public void beforeEach() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        if (repositoryList != null && !repositoryList.isEmpty()) {
            repositoryList.forEach(CrudRepository::deleteAll);
        }

        if (amazonSQS != null) {
            amazonSQS.listQueues()
                    .getQueueUrls()
                    .forEach(q -> amazonSQS.purgeQueue(new PurgeQueueRequest(q)));
        }
    }

    protected Integer countQueueMessages(String queue) {
        return super.countQueueMessages(amazonSQS, queue);
    }
}
