package com.javagym.microservice_two.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javagym.microservice_two.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class RabbitMessageProducerService {
    private static final Logger log = LoggerFactory.getLogger(RabbitMessageProducerService.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMessageProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public <T> void sendMessageToQueue(final T messageData) {
        try {
            log.info("[(message:{})] [(exchange:{})]", messageData, RabbitMQConfig.EXCHANGE_NAME);
            this.rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.ROUTING_KEY_NAME, new ObjectMapper().writeValueAsString(messageData));
        } catch (MessagingException | JsonProcessingException e) {
            log.error("[(data:{})] [(error:{})]", messageData, e.getMessage());
        }
    }
}