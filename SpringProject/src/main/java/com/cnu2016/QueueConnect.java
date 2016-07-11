package com.cnu2016;

/**
 * Created by Piyush on 7/11/16.
 */
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueConnect {

    private static AmazonSQSClient sqs;

    public QueueConnect()
    {
        sqs = new AmazonSQSClient(new DefaultAWSCredentialsProviderChain());
        this.sqs = sqs;
    }
    public void sendMessage(String message) {
        try {
            String myQueueUrl = sqs.getQueueUrl("cnu2016_pagarwal_assignment05").getQueueUrl();
            sqs.sendMessage(new SendMessageRequest(myQueueUrl, message));

        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
            //TODO: catch exception
        } catch (AmazonClientException ace) {
            ace.printStackTrace();
            //TODO: catch exception
        }
    }
}
