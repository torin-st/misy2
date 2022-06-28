package com.slyadz.misy2.userssevice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSenderService {
	private final Logger logger = LoggerFactory.getLogger(KafkaSenderService.class);
	private final KafkaTemplate<String, String> kafkaTemplate;

	KafkaSenderService(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(String message, String topicName) {
		logger.info("Sending : {}", message);
		logger.info("--------------------------------");
		kafkaTemplate.send(topicName, message);
	}

}
