package com.slyadz.misy2.userssevice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.slyadz.misy2.usersservice.api.UserTopics;
import com.slyadz.misy2.usersservice.api.event.UserVerificationFailedEventData;
import com.slyadz.misy2.usersservice.api.event.UserVerificationSuccessEventData;

@Service
public class KafkaSenderService {
	private final Logger logger = LoggerFactory.getLogger(KafkaSenderService.class);
	private final KafkaTemplate<String, String> stringKafkaTemplate;
	private final KafkaTemplate<String, UserVerificationFailedEventData> userVerificationFailedEventDataKafkaTemplate;
	private final KafkaTemplate<String, UserVerificationSuccessEventData> userVerificationSuccessEventDataKafkaTemplate;

	public KafkaSenderService(KafkaTemplate<String, String> stringKafkaTemplate,
			KafkaTemplate<String, UserVerificationFailedEventData> userVerificationFailedEventDataKafkaTemplate,
			KafkaTemplate<String, UserVerificationSuccessEventData> userVerificationSuccessEventDataKafkaTemplate) {
		this.stringKafkaTemplate = stringKafkaTemplate;
		this.userVerificationFailedEventDataKafkaTemplate = userVerificationFailedEventDataKafkaTemplate;
		this.userVerificationSuccessEventDataKafkaTemplate = userVerificationSuccessEventDataKafkaTemplate;
	}

	public void sendMessage(String message, String topicName) {
		logger.info("Sending : {}", message);
		logger.info("--------------------------------");
		stringKafkaTemplate.send(topicName, message);
	}

	public void sendMessage(UserVerificationFailedEventData eventData) {
		userVerificationFailedEventDataKafkaTemplate.send(UserTopics.VERIFICATION_FAILED, eventData);
	}

	public void sendMessage(UserVerificationSuccessEventData eventData) {
		userVerificationSuccessEventDataKafkaTemplate.send(UserTopics.VERIFICATION_SUCCESS, eventData);
	}

}
