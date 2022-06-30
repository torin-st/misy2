package com.slyadz.misy2.greetingsevice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
class KafkaListenerService {
	private static Logger logger = LoggerFactory.getLogger(KafkaListenerService.class);
	private final static String USERS_TOPIC = "users";

	@KafkaListener(topics = USERS_TOPIC)
	void listener(String message) {
		logger.info("Listener: " + message);
	}

}
