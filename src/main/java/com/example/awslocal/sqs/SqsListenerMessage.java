package com.example.awslocal.sqs;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class SqsListenerMessage {

    static final Logger LOGGER = LoggerFactory.getLogger(SqsListenerMessage.class);

    @JmsListener(destination = "${aws.local.sqs.name-queue-1}")
    public void consumerQueue1(@Payload Message message,  @Headers MessageHeaders headers) throws JMSException {
        LOGGER.debug("Calling consumerQueue1()" );
        LOGGER.debug("headers = {}",headers);
        LOGGER.debug("message = {}", ((TextMessage) message ).getText());

    }

    @JmsListener(destination = "${aws.local.sqs.name-queue-2}")
    public void consumerQueue2(@Payload Message message,  @Headers MessageHeaders headers) throws JMSException {
        LOGGER.debug("Calling consumerQueue2()" );
        LOGGER.debug("headers = {}",headers);
        LOGGER.debug("message = {}", ((TextMessage) message ).getText());

    }
    
}
