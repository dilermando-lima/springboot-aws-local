package com.example.awslocal.sqs;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@Configuration
public class SqsConfig {

    @Autowired SqsProperties sqsProperties;

    @Bean
    public ConnectionFactory jmsConnectionSQSAmazonFactory() {

        AmazonSQSClientBuilder sqsClientBuilder =  AmazonSQSClientBuilder.standard();
        sqsClientBuilder.withCredentials(new EnvironmentVariableCredentialsProvider());
        sqsClientBuilder.setEndpointConfiguration(new EndpointConfiguration(sqsProperties.getEndpoint(), sqsProperties.getRegion()));
  
        return new SQSConnectionFactory(
                    new ProviderConfiguration(),
                    sqsClientBuilder.build()
                );
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory( @Autowired ConnectionFactory connectionFactory) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //factory.setConcurrency("3-10")
        if( (  sqsProperties.getEnableAcknowledgeMode() != null &&  sqsProperties.getEnableAcknowledgeMode()) ){
            factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        }
        factory.setAutoStartup(sqsProperties.getEnableListenerQueue() ||  sqsProperties.getEnableListenerQueue() != null);
        return factory;
    }

    
    @Bean
    public JmsTemplate defaultJmsTemplate(@Autowired ConnectionFactory connectionFactory) {
        return  new JmsTemplate(connectionFactory);
    }


}
