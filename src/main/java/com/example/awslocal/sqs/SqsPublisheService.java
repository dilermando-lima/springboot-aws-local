package com.example.awslocal.sqs;

import java.util.Map;
import java.util.UUID;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SqsPublisheService {


    static final Logger LOGGER = LoggerFactory.getLogger(SqsPublisheService.class);

    @Autowired SqsProperties sqsProperties;

    @Autowired
    private JmsTemplate jmsTemplate;

    public void publishQueue1(String message, Map<String, Object> headers) {
        LOGGER.debug("Calling publishQueue1()");

        publishMessageSQS(sqsProperties.getNameQueue1(), message , headers);
    }

    public void publishQueue2(String message, Map<String, Object> headers) {
        LOGGER.debug("Calling publishQueue2()");

        publishMessageSQS(sqsProperties.getNameQueue2(), message , headers);
    }

    private void publishMessageSQS(String queueName, String message, Map<String, Object> headers){

        LOGGER.debug("Calling publishMessageSQS() : queueName = {} , message = {} , headers = {} " , queueName ,  message ,  headers );

        jmsTemplate.convertAndSend(queueName, message , postProcessor -> {

            postProcessor.setJMSCorrelationID("@DILERMANDO.LIMA-" + UUID.randomUUID().toString());

            LOGGER.debug("id_message_sent = {} ", postProcessor.getJMSCorrelationID());

            headers.entrySet().stream().forEach(header ->  
                    {
                        try {
                            postProcessor.setObjectProperty(header.getKey(), header.getValue());
                        } catch (JMSException e){
                            LOGGER.error(e.getMessage(), e);
                            e.printStackTrace();
                        }
                    }
            );
            return postProcessor;
        });
    }


}
