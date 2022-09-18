package com.awssqsdemo.awssqsdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SQQController{

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    @GetMapping("/put/{msg}")
    public void putMessageToQueue(@PathVariable("msg") String message) {
        queueMessagingTemplate.send(endpoint,
                MessageBuilder.withPayload(message).build());
    }

    // provide exact queue name which is in AWS console
    @SqsListener("my-sqs-queue")
    public void LoadMessagesFromQueue(String msg) {
        System.out.println("Queue Messages -> " + msg);
    }
}
