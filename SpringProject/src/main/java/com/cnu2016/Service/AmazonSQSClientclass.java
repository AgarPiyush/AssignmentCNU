package com.cnu2016.Service;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQSClient;
import org.springframework.stereotype.Component;

/**
 * Created by Piyush on 7/11/16.
 */

// TODO Autowire with QueueConnect

@Component
public class AmazonSQSClientclass {
    private AmazonSQSClient amazonsqs;

    AmazonSQSClientclass()
    {
        amazonsqs= new AmazonSQSClient(new DefaultAWSCredentialsProviderChain());
    }
    public AmazonSQSClient getAmazonsqs() {
        return amazonsqs;
    }

    public void setAmazonsqs(AmazonSQSClient amazonsqs) {
        this.amazonsqs = amazonsqs;
    }


}
