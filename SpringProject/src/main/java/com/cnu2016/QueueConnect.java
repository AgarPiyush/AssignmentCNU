package com.cnu2016;

/**
 * Created by Piyush on 7/11/16.
 */
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class QueueConnect {

    private static AmazonSQSClient sqs;
    private static final Logger logger = LoggerFactory.getLogger(QueueConnect.class);

    public QueueConnect()
    {
        sqs = new AmazonSQSClient(new DefaultAWSCredentialsProviderChain());
    }
    public void sendMessage(String message) {
            logger.info("Messgae sending to queue"+message);
            String myQueueUrl = sqs.getQueueUrl("cnu2016_pagarwal_assignment05").getQueueUrl();
            sqs.sendMessage(new SendMessageRequest(myQueueUrl, message));
    }
}
