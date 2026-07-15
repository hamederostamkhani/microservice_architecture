package com.javagym.microservice_one.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javagym.microservice_one.config.RabbitMQConfig;
import com.javagym.microservice_one.entity.RabbitMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMessageConsumerService {
	private static final Logger log = LoggerFactory.getLogger(RabbitMessageConsumerService.class);

	@Async
	@RabbitListener(queues = RabbitMQConfig.MS2_QUEUE)
	public void process(String data) {
		try {
			RabbitMessage<String, String> receivedMessage = new ObjectMapper().readValue(data, RabbitMessage.class);
			log.info("[(start:AddProductToSeller)] [(queue:{})] [(uid:{})] [(data:{})]", RabbitMQConfig.MS1_QUEUE, receivedMessage.getUid(), receivedMessage);
			// push notification
			log.info("[(end:AddProductToSeller)] [(uid:{})] [(data:{})]", receivedMessage.getUid(), "notification pushed");
		} catch (IOException e) {
			log.error("[(errorData:{})] [(errorMessage:{})]", data, e.getMessage());
		}
	}
}